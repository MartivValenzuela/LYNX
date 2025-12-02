package persistencia;

import modelo.Persona;
import utilidades.GestionHuertosException;
import java.io.*;
import java.util.ArrayList;

import modelo.*;

public class GestionHuertosIO {
    public void savePersonas(Persona[] personas) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Personas.obj"))) {

            if(personas != null){
                for(Persona p : personas){
                    if(p != null){
                        out.writeObject(p);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            throw new GestionHuertosException("Error al abrir/crear el archivo Personas.obj");
        } catch (IOException e) {
            throw new GestionHuertosException("Error al intentar escribir en el archivo Personas.obj");
        }
    }

    public void saveCultivos(Cultivo[] cultivos) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Cultivos.obj"))) {

            if(cultivos != null){
                for(Cultivo c : cultivos){
                    if(c != null){
                        out.writeObject(c);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new GestionHuertosException("Error al abrir/crear el archivo Cultivos.obj");
        } catch (IOException e) {
            throw new GestionHuertosException("Error al intentar escribir en el archivo Cultivos.obj");
        }
    }

    public void savePlanesCosecha(PlanCosecha[] planes) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("PlanesCosecha.obj"))) {

            if(planes != null){
                for(PlanCosecha p : planes){
                    if(p != null){
                        out.writeObject(p);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new GestionHuertosException("Error al abrir/crear el archivo PlanesCosecha.obj");
        } catch (IOException e) {
            throw new GestionHuertosException("Error al intentar escribir en el archivo PlanesCosecha.obj");
        }
    }

    //lectura
    public Persona[] readPersonas() throws GestionHuertosException {
        File file = new File("Personas.obj");
        if (!file.exists()) {
            throw new GestionHuertosException("Archivo Personas.obj no encontrado");
        }

        ArrayList<Persona> lista = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            while(true){
                Object obj = in.readObject();
                if(obj instanceof Persona){
                    lista.add((Persona)obj);
                }else {
                    throw new GestionHuertosException("Objeto leido no corresponde a persona");
                }
            }
        }catch(EOFException ignored){
            //esto es para ponerle fin normal al archivo
        }catch (IOException | ClassNotFoundException e) {
            throw new GestionHuertosException("Error al intentar leer datos del archivo Personas.obj");
        }
        return lista.toArray(new Persona[0]);
    }

    public Cultivo[] readCultivos() throws GestionHuertosException {
        File file = new File("Cultivos.obj");
        if (!file.exists()) {
            throw new GestionHuertosException("Archivo Cultivos.obj no encontrado");
        }

        ArrayList<Cultivo> lista = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            while(true){
                Object obj = in.readObject();
                if(obj instanceof Cultivo){
                    lista.add((Cultivo)obj);
                }else {
                    throw new GestionHuertosException("Objeto leído no corresponde a Cultivo");
                }
            }
        } catch (EOFException ignored) {
            //esto es para ponerle fin normal al archivo
        }catch (IOException | ClassNotFoundException e) {
            throw new GestionHuertosException("Error al intentar leer datos del archivo Cultivos.obj");
        }
        return lista.toArray(new Cultivo[0]);
    }

    public PlanCosecha[] readPlanesCosecha() throws GestionHuertosException {
        File file = new File("PlanesCosecha.obj");
        if (!file.exists()) {
            throw new GestionHuertosException("Archivo PlanesCosecha.obj no encontrado");
        }

        ArrayList<PlanCosecha> lista = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            while(true){
                Object obj = in.readObject();
                if(obj instanceof PlanCosecha){
                    lista.add((PlanCosecha)obj);
                } else {
                    throw new GestionHuertosException("Objeto leído no corresponde a PlanCosecha");
                }
            }
        } catch (EOFException e) {
            //esto es para ponerle fin normal al archivo
        } catch (IOException | ClassNotFoundException e) {
            throw new GestionHuertosException("Error al intentar leer datos del archivo PlanesCosecha.obj");
        }
        return lista.toArray(new PlanCosecha[0]);
    }
}
