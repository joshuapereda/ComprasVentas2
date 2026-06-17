package com.blumbit.CompraVentas2.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.blumbit.CompraVentas2.dto.CategoriaDto;

@Service
public class CategoriaServiceMyBatis implements ICategoriaService {
    //ACA SE DA LA LOGICA DE LOS METODOS DEFINIDOS EN LA INTERFAZ ICATEGORIASERVICE,

    @Override
    public List<CategoriaDto> listarCategorias() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarCategorias'");
    }

    @Override
    public CategoriaDto obtenerCategoriaPorId(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerCategoriaPorId'");
    }

    @Override
    public CategoriaDto crearCategoria(CategoriaDto categoria) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crearCategoria'");
    }

    @Override
    public CategoriaDto actualizarCategoria(Integer id, CategoriaDto categoriaActualizada) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizarCategoria'");
    }

    @Override
    public void eliminarCategoria(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarCategoria'");
    }

}
