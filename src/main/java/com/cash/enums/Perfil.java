package com.cash.enums;

public enum Perfil {
    ADMIN(0, "ROLE_ADMIN"),
    GERENTE(1, "ROLE_GERENTE"),
    USER(2, "ROLE_USER"),
    AVULSO(3,"ROLE_AVULSO");

    private Integer codigo;
    private String descricao;

    private Perfil(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Perfil toEnum(Integer cod) {
        if(cod == null)
            return null;
        for (Perfil x: Perfil.values()) {
            if(cod.equals(x.getCodigo()))
                return x;
        }
        throw new IllegalArgumentException("Perfil invalido.");
    }


}

