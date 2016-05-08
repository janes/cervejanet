package br.com.ipt.web.rest.mapper;

import br.com.ipt.domain.*;
import br.com.ipt.web.rest.dto.CatalogoDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Catalogo and its DTO CatalogoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatalogoMapper {

    CatalogoDTO catalogoToCatalogoDTO(Catalogo catalogo);

    List<CatalogoDTO> catalogosToCatalogoDTOs(List<Catalogo> catalogos);

    @Mapping(target = "produtos", ignore = true)
    Catalogo catalogoDTOToCatalogo(CatalogoDTO catalogoDTO);

    List<Catalogo> catalogoDTOsToCatalogos(List<CatalogoDTO> catalogoDTOs);
}
