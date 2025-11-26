package controlador;

import utilidades.GestionHuertosException;
import utilidades.Persona;
import java.io.*;
import modelo.*;

public class GestionHuertosIO {
    public void savePersonas(Persona[] personas) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Personas.obj"))) {
            out.writeObject(personas);
        } catch (FileNotFoundException e) {
            throw new GestionHuertosException("Error al abrir/crear el archivo Personas.obj");
        } catch (IOException e) {
            throw new GestionHuertosException("Error al intentar escribir en el archivo Personas.obj");
        }
    }

    public void saveCultivos(Cultivo[] cultivos) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Cultivos.obj"))) {
            out.writeObject(cultivos);
        } catch (FileNotFoundException e) {
            throw new GestionHuertosException("Error al abrir/crear el archivo Cultivos.obj");
        } catch (IOException e) {
            throw new GestionHuertosException("Error al intentar escribir en el archivo Cultivos.obj");
        }
    }

    public void savePlanesCosecha(PlanCosecha[] planes) throws GestionHuertosException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("PlanesCosecha.obj"))) {
            out.writeObject(planes);
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

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Persona[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new GestionHuertosException("Error al intentar leer datos del archivo Personas.obj");
        } catch (ClassCastException e) {
            throw new GestionHuertosException("Objeto leído no corresponde a Persona");
        }
    }

    public Cultivo[] readCultivos() throws GestionHuertosException {
        File file = new File("Cultivos.obj");
        if (!file.exists()) {
            throw new GestionHuertosException("Archivo Cultivos.obj no encontrado");
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (Cultivo[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new GestionHuertosException("Error al intentar leer datos del archivo Cultivos.obj");
        } catch (ClassCastException e) {
            throw new GestionHuertosException("Objeto leído no corresponde a Cultivo");
        }
    }

    public PlanCosecha[] readPlanesCosecha() throws GestionHuertosException {
        File file = new File("PlanesCosecha.obj");
        if (!file.exists()) {
            throw new GestionHuertosException("Archivo PlanesCosecha.obj no encontrado");
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (PlanCosecha[]) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new GestionHuertosException("Error al intentar leer datos del archivo PlanesCosecha.obj");
        } catch (ClassCastException e) {
            throw new GestionHuertosException("Objeto leído no corresponde a PlanCosecha");
        }
    }
}