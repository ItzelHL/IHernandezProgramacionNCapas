package com.digis01.IHernandezProgramacionNCapas.DAO;

import com.digis01.IHernandezProgramacionNCapas.ML.Colonia;
import com.digis01.IHernandezProgramacionNCapas.ML.Direccion;
import com.digis01.IHernandezProgramacionNCapas.ML.Estado;
import com.digis01.IHernandezProgramacionNCapas.ML.Municipio;
import com.digis01.IHernandezProgramacionNCapas.ML.Pais;
import com.digis01.IHernandezProgramacionNCapas.ML.Result;
import com.digis01.IHernandezProgramacionNCapas.ML.Rol;
import com.digis01.IHernandezProgramacionNCapas.ML.Usuario;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO 
{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll(Usuario usuario) 
    {
        Result result = new Result();

        try 
        {
            jdbcTemplate.execute("CALL UsuarioDireccionGetAll(?, ?, ?, ?, ?)", (CallableStatementCallback<Integer>) callableStatement -> 
            {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.Rol.getIdRol());
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.objects = new ArrayList<>();

                while (resultSet.next()) 
                {
                    int idUsuario = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && idUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) 
                    {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direccion.add(direccion);
                    } else 
                    {
                        Usuario usuarioBD = new Usuario();
                        
                        usuarioBD.setImagen(resultSet.getString("ImagenUsuario"));
                        usuarioBD.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioBD.setUsername(resultSet.getString("Username"));
                        usuarioBD.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioBD.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioBD.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioBD.setEmail(resultSet.getString("Email"));
                        usuarioBD.setPassword(resultSet.getString("Password"));
                        usuarioBD.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuarioBD.setSexo(resultSet.getString("Sexo"));
                        usuarioBD.setTelefono(resultSet.getString("Telefono"));
                        usuarioBD.setCelular(resultSet.getString("Celular"));
                        usuarioBD.setCurp(resultSet.getString("Curp"));

                        usuarioBD.Rol = new Rol();
                        usuarioBD.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuarioBD.Rol.setNombre(resultSet.getString("NombreRol"));

                        int idDireccion;
                        if ((idDireccion = resultSet.getInt("IdDireccion")) != 0)
                        {
                            usuarioBD.Direccion = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                            usuarioBD.Direccion.add(direccion);
                        }
                        result.objects.add(usuarioBD);
                    }
                }
                    result.correct = true;
                    return 1;
            });
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result GetById(int idUsuario) 
    {
        Result result = new Result();
        
        try 
        {
            jdbcTemplate.execute("CALL UsuarioGetById(?, ?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, idUsuario);
                
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                if(resultSet.next())
                {
                    Usuario usuario = new Usuario();
                    
                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setImagen(resultSet.getString("ImagenUsuario"));
                    usuario.setUsername(resultSet.getString("Username"));
                    usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setCurp(resultSet.getString("Curp"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setPassword(resultSet.getString("Password"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setCelular(resultSet.getString("Celular"));

                    usuario.Rol = new Rol();
                    usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    
                    result.object = usuario;
                }
                result.correct = true;
                return true;
            });
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result DireccionesByIdUsuario(int idUsuario)
    {
        Result result = new Result();
        
        try {
            jdbcTemplate.execute("{CALL UsuarioDireccionGetByIdUsuario(?, ?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.setInt(2, idUsuario);
                
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                if (resultSet.next()) 
                {
                    Usuario usuario = new Usuario();

                    usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuario.setImagen(resultSet.getString("ImagenUsuario"));
                    usuario.setUsername(resultSet.getString("Username"));
                    usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                    usuario.setSexo(resultSet.getString("Sexo"));
                    usuario.setCurp(resultSet.getString("Curp"));
                    usuario.setEmail(resultSet.getString("Email"));
                    usuario.setPassword(resultSet.getString("Password"));
                    usuario.setTelefono(resultSet.getString("Telefono"));
                    usuario.setCelular(resultSet.getString("Celular"));

                    usuario.Rol = new Rol();
                    usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuario.Rol.setNombre(resultSet.getString("NombreRol"));

                    int idDireccion;
                    if ((idDireccion = resultSet.getInt("IdDireccion")) != 0) 
                    {

                        usuario.Direccion = new ArrayList<>();

                        do {
                            Direccion direccion = new Direccion();
                            direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                            direccion.Colonia = new Colonia();
                            direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));

                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                            usuario.Direccion.add(direccion);
                        } while (resultSet.next());
                    }
                    result.object = usuario;
                }
                
                result.correct = true;
                return 1;
            });
            
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result Add(Usuario usuario)
    {
        Result result = new Result();
        
        try {
            result.correct = jdbcTemplate.execute("{CALL UsuarioDireccionAdd(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}", 
                    (CallableStatementCallback<Boolean>) callableStatement -> {
                        callableStatement.setString(1, usuario.getImagen());
                        callableStatement.setString(2, usuario.getUsername());
                        callableStatement.setString(3, usuario.getNombre());
                        callableStatement.setString(4, usuario.getApellidoPaterno());
                        callableStatement.setString(5, usuario.getApellidoMaterno());
                        //Aqui van la conversión de util a sql
//            Long fechNac = usuario.getFechaNacimiento().getTime();
//            java.sql.Date sqlDate = new java.sql.Date(fechNac);
//            callableStatement.setDate(4, sqlDate);
                        callableStatement.setDate(6, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                        callableStatement.setString(7, usuario.getSexo());
                        callableStatement.setString(8, usuario.getCurp());
                        callableStatement.setString(9, usuario.getEmail());
                        callableStatement.setString(10, usuario.getPassword());
                        callableStatement.setString(11, usuario.getTelefono());
                        callableStatement.setString(12, usuario.getCelular());

                        callableStatement.setInt(13, usuario.Rol.getIdRol());

                        callableStatement.setString(14, usuario.Direccion.get(0).getCalle());
                        callableStatement.setString(15, usuario.Direccion.get(0).getNumeroExterior());
                        callableStatement.setString(16, usuario.Direccion.get(0).getNumeroInterior());

                        callableStatement.setInt(17, usuario.Direccion.get(0).Colonia.getIdColonia());
                        
                        int isCorrect = callableStatement.executeUpdate();

                        if (isCorrect == -1) {
                            return true;
                        }
                        return false;
                    });
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    @Override
    public Result AddDireccion(int idUsuario)
    {
        Result result = new Result();
        
        try 
        {
            jdbcTemplate.execute("CALL DireccionADDUsuario(?, ?, ?, ?, ?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                Usuario usuario = new Usuario();
                callableStatement.setString(1, usuario.Direccion.get(0).getCalle());
                callableStatement.setString(2, usuario.Direccion.get(0).getNumeroExterior());
                callableStatement.setString(3, usuario.Direccion.get(0).getNumeroInterior());
                callableStatement.setInt(4, usuario.Direccion.get(0).Colonia.getIdColonia());
                callableStatement.setInt(5, idUsuario);
                
                 int isCorrect = callableStatement.executeUpdate();

                if (isCorrect == -1) 
                {
                    return true;
                }
                return false;
            });
            result.correct = true;
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    @Override
    public Result UpdateUsuario(Usuario usuario)
    {
        Result result = new Result();
        
        try 
        {
            jdbcTemplate.execute("CALL UsuarioUpdateSP(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getUsername());
                callableStatement.setString(3, usuario.getNombre());
                callableStatement.setString(4, usuario.getApellidoPaterno());
                callableStatement.setString(5, usuario.getApellidoMaterno());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, usuario.getPassword());
                callableStatement.setDate(8, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(9, usuario.getSexo());
                callableStatement.setString(10, usuario.getTelefono());
                callableStatement.setString(11, usuario.getCelular());
                callableStatement.setString(12, usuario.getCurp());
                callableStatement.setInt(13, usuario.Rol.getIdRol());

                callableStatement.executeUpdate();
                return true;
            });
            
            result.correct = true;
        } catch (Exception ex) 
        {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
}
