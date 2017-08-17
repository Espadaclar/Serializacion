
import java.io.*;
import java.util.Calendar;
import java.util.*;
/**
 * SERIALIZACIÓN.
 * Es convertir un objeto que hayamos creado en java en una sucesión de bytes, con el objetivo de poder almacenar ese objeto en el discoDuro, para
 * en un futuro poder restarurarlo. Y sobre todo poder  mover ese archivo através de la red para pasarlo a otros ordenadores y en esos otros
 * ordenadores poder pasarlos a su estado normal, como estaba antes de ser serializado.
 * 
 * -----> Para ello hemos de implementar la interface ‘Serializable’ al objeto que vallamos a ‘Serializar’.
 * Con ‘Serializable le decimos a java que la cl que la implemente es suceptible de ser serializable.
 * 
 * 
 *  ‘OjectOutputStream’  es un cl que construye un flujo de datos que permite construir un objeto desde dentro hacia afuera, desde nuestro programa 
 *                     hacia un medio de almacenamiento o hacia un ordenador remoto.
 *  ‘OjectInputStream’  es una cl que construye un flujo de datos que permite construir un objeto desde fuera hacia fuera.


 * @author FranciscoJavier
 */
public class Serializacion {

    public static void main(String[] args) {
        Administrador jefe = new Administrador("Juan", 55, 8000, 2000, 12, 23);
        jefe.setIncentivo(5000);

        // Array de objetos Empleado
        Empleado[] personal = new Empleado[3];
        personal[0] = jefe;
        personal[1] = new Empleado("Pedro", 22, 1300, 2012, 12, 22);
        personal[2] = new Empleado("Marta", 33, 1111, 2007, 7, 100);
        
        
        try {
            //SE TRATA DE SERIALIZAR EL OBJETO ARRAY DE TIPO EMPLEADO, Y ALMACENARLE EN EL DISCO_DURO, para ello; 
            // --- 1º implementamos la interface Serializable a la cl Empleado.
            // --- 2º instanciamos el flujo de datos con la cl 'ObjectOutputStream' para que nuestro objeto viaje al exterior, 
            //      nos pide un parámetro de tipo OutputStream()  --le ponemos uno de tipo File que hereda de OutputStream para
            //       poder pasarle la ruta donde almacenar el array.
            ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream("C:/Users/Usuario/Desktop/empleado/empleado.dat"));
            escribiendo_fichero.writeObject(personal);//  ---escribe en el fichero el contenido del Array.   
            
            // System.out.println(jefe + "\n" + personal[1] + "\n" + personal[2]);
            escribiendo_fichero.close();
        } catch (Exception ex) {
            System.out.println("");
        }
        
        try {
            //instanciamos ObjectInputStream para indicar la ruta del archivo que vamos a recuperar
            ObjectInputStream recupera_fichero = new ObjectInputStream(new FileInputStream("C:/Users/Usuario/Desktop/empleado/empleado.dat"));
            // ---- IMPORTANTE, TENER EN CUENTA EL TIPO DE OBJETO QUE VAMOS A TRATAR.
            //recordemos que el archivo es un objeto array de tipo Empleado, por lo que tenemos  
            //que almacenar en otro objeto del mismo tipo. Empleado
            
            //En la api vemos que el mt ‘readObject()’ .Devuelve un objeto de tipo Object. Que no podemos almacenar 
            //en un Array, por eso hacemos ‘casting’.Empleado[] personal_recuperado = (Empleado[]) recupera_fichero.readObject(); 
            //-‘casting’  le dice al mt readObject() que lo que devuelva lo convierta en un Array de tipo Empleado.
            Empleado[] personal_recuperado = (Empleado[])recupera_fichero.readObject();
            
            //comprueba el contenido del Array 'personal_recuperado.'
            for(Empleado emple: personal_recuperado){
            System.out.println(emple);
            }
            
            recupera_fichero.close();
        } catch (Exception ex) {
            System.out.println("");
        }               
    }
}

class Empleado implements Serializable{

    private String name;
    private int edad;
    private Date fechaContrato;
    private double sueldo;

    public Empleado(String name, int edad, double sueldo, int anno, int mes, int dia) {
        this.name = name;
        this.edad = edad;
        this.sueldo = sueldo;
        GregorianCalendar calendario = new GregorianCalendar(anno, mes, dia);
        fechaContrato = calendario.getTime();
    }

    public String getName() {
        return name;
    }

    public int getEdad() {
        return edad;
    }

    public Date getFechaContrato() {
        return fechaContrato;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void subirSueldo(double porcentaje) {
        double aumento = sueldo * porcentaje;
        sueldo += aumento;
    }

    @Override
    public String toString() {
        return "name = " + name + ", edad = " + edad + ", fechaContrato = " + fechaContrato + ", sueldo = " + sueldo;
    }

}

class Administrador extends Empleado {

    private double incentivo;

    public Administrador(String name, int edad, double sueldo, int anno, int mes, int dia) {
        super(name, edad, sueldo, anno, mes, dia);
        incentivo = 0;
    }

    public double getSueldo() {
        double sueldoBase = super.getSueldo();
        return sueldoBase + incentivo;
    }

    public void setIncentivo(double incen) {
        incentivo = incen;
    }

    @Override
    public String toString() {
        return super.toString() + " incentivo " + incentivo;
    }
}
