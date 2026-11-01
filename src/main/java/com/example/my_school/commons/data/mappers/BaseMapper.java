package com.example.my_school.commons.data.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Named;

import java.util.List;

public abstract class BaseMapper<Rq, Rp, Po> {
    @Named("toPOJO")
    public abstract Po toPojo(Rq request);

    @IterableMapping(qualifiedByName = "toPOJO")
    public abstract List<Po> toPOJOs(List<Rq> requests);

    @Named("toResponse")
    public abstract Rp toResponse(Po po);

    @IterableMapping(qualifiedByName = "toResponse")
    public abstract List<Rp> toResponses(List<Po> pojos);

}
