import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graficas extends JFrame implements ActionListener {
    private JButton botonCargarArchivo, botonCrearGrafica;
    private JComboBox<String> comboBoxFiltro1, comboBoxFiltro2, comboBoxFiltro3, comboBoxFiltro4;
    private JTextArea areaTexto;
    private ArrayList<Dato> datos = new ArrayList<>();
    
    public Graficas() {
        super("Programa de gráficas");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));
        
        add(new JLabel("Archivo:"));
        botonCargarArchivo = new JButton("Cargar archivo");
        botonCargarArchivo.addActionListener(this);
        add(botonCargarArchivo);
        
        add(new JLabel("Filtro 1:"));
        comboBoxFiltro1 = new JComboBox<>(new String[] {"Hombres", "Mujeres"});
        add(comboBoxFiltro1);
        
        add(new JLabel("Filtro 2:"));
        comboBoxFiltro2 = new JComboBox<>(new String[] {"Mayores de 50 años", "Menores de 50 años"});
        add(comboBoxFiltro2);
        
        add(new JLabel("Filtro 3:"));
        comboBoxFiltro3 = new JComboBox<>(new String[] {"Ciudad de Bogotá", "Otra ciudad"});
        add(comboBoxFiltro3);
        
        add(new JLabel("Filtro 4:"));
        comboBoxFiltro4 = new JComboBox<>(new String[] {"Antecedentes médicos de Hipertensión", "Sin antecedentes médicos de Hipertensión"});
        add(comboBoxFiltro4);
        
        botonCrearGrafica = new JButton("Crear gráfica");
        botonCrearGrafica.addActionListener(this);
        add(botonCrearGrafica);
        
        areaTexto = new JTextArea();
        add(areaTexto);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == botonCargarArchivo) {
            JFileChooser chooser = new JFileChooser();
            int resultado = chooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = chooser.getSelectedFile();
                try {
                    
                } catch (IOException e) {
                    areaTexto.setText("Error");
                }
            }
        } else if (evento.getSource() == botonCrearGrafica) {
            ArrayList<Dato> datosFiltrados = filtrarDatos((String) comboBoxFiltro1.getSelectedItem(), (String) comboBoxFiltro2.getSelectedItem(), (String) comboBoxFiltro3.getSelectedItem(), (String) comboBoxFiltro4.getSelectedItem());
            ChartPanel panelGrafica = crearGrafica(datosFiltrados);
            getContentPane().add(panelGrafica);
            revalidate();
            repaint();
        }
    }

    private ArrayList<Dato> filtrarDatos(String filtro1, String filtro2, String filtro3, String filtro4) {
        ArrayList<Dato> datosFiltrados = new ArrayList<>();
        for (Dato dato : datos) {
            if (dato.getGenero().equals(filtro1) && 
                dato.getEdad() > 50 && filtro2.equals("Mayores de 50 años") ||
                dato.getEdad() <= 50 && filtro2.equals("Menores de 50 años") &&
                dato.getCiudad().equals(filtro3) &&
                dato.getAntecedentesMedicos().equals(filtro4)) {
                datosFiltrados.add(dato);
            }
        }
        return datosFiltrados;
    }

    private ChartPanel crearGrafica(ArrayList<Dato> datos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Dato dato : datos) {
            dataset.addValue(dato.getValor(), dato.getCategoria(), dato.getEtiqueta());
        }
        JFreeChart chart = ChartFactory.createBarChart("Gráfica", "Categoría", "Valor", dataset);
        return new ChartPanel(chart);
    }

    public static void main(String[] args) {
        Graficas programa = new Graficas();
    }
}

