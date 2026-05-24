package com.example.myschool.commons.data.mappers;

import org.mapstruct.Named;

public abstract class BaseMapper<Rq, Rp, Po> {
    @Named("toPOJO")
    public abstract Po toPojo(Rq request);

    public abstract Rp toResponse(Po po);
}
