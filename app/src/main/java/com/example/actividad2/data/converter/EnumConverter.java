package com.example.actividad2.data.converter;

import androidx.room.TypeConverter;

import com.example.actividad2.data.entity.enums.EstadoServicio;
import com.example.actividad2.data.entity.enums.TipoServicio;

public class EnumConverter{
    @TypeConverter
    public static String fromTipoServicio(TipoServicio tipoServicio) {
        return tipoServicio == null ? null :tipoServicio.name();
    }

    @TypeConverter
    public static TipoServicio toTipoServicio(String valor) {
        return valor == null ? null : TipoServicio.valueOf(valor);
    }

    @TypeConverter
    public static String fromEstadoServicio(EstadoServicio estado) {
        return estado == null ? null : estado.name();
    }

    @TypeConverter
    public static EstadoServicio toEstadoServicio(String valor) {
        return valor == null ? null : EstadoServicio.valueOf(valor);
    }
}