package com.example.actividad2.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.actividad2.data.converter.EnumConverter;
import com.example.actividad2.data.dao.ClienteDao;
import com.example.actividad2.data.dao.ServicioDao;
import com.example.actividad2.data.entity.Cliente;
import com.example.actividad2.data.entity.Servicio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Cliente.class, Servicio.class}, version = 3, exportSchema = false)
@TypeConverters(EnumConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ClienteDao clienteDao();
    public abstract ServicioDao servicioDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration(true) // si se modifica entidades Room reconstruye la BD automáticamente
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ClienteDao clienteDao = INSTANCE.clienteDao();
                ServicioDao servicioDao = INSTANCE.servicioDao();
            });
        }
    };

}