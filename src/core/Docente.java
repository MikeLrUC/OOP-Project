package core;

import java.util.ArrayList;

/**
 * Represents a Docente.
 */
public class Docente extends Pessoa {

    private String mecano;
    private String researchArea;
    private ArrayList<Estudante> orientados;
    private ArrayList<Project> projetos;

    /**
     * Creates a Docente with his name, email, research center, mecanographic number and research area.
     * @param nome String with the docente's name.
     * @param email String with the docente's email.
     * @param researchCenter ResearchCenter object with the docente's research center.
     * @param mecano String with the docente's mecanographic number.
     * @param researchArea String with the docente's research area.
     */
    public Docente(String nome, String email, ResearchCenter researchCenter,String mecano, String researchArea){
        super(nome, email, researchCenter);
        this.mecano = mecano;
        this.researchArea = researchArea;
    }

    /**
     * Gets the docente's mecanographic number.
     * @return String with the docentes's mecanographic number.
     */
    public String getMecano() {
        return this.mecano;
    }

    /**
     * Sets the docente's mecanographic number.
     * @param mecano String with the docente's mecanographic number.
     */
    public void setMecano(String mecano) {
        this.mecano = mecano;
    }

    /**
     * Gets the docente's research area.
     * @return String with the docente's research area.
     */
    public String getResearchArea() {
        return this.researchArea;
    }

    /**
     * Sets the docente's research area.
     * @param researchArea String with the docente's research area.
     */
    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    /**
     * Gets the docente's oriented estudantes.
     * @return ArrayList with the docente's oriented estudantes.
     */
    public ArrayList<Estudante> getOrientados() {
        return this.orientados;
    }

    /**
     * Sets the docente's oriented estudantes.
     * @param orientados ArrayList with the docente's oriented estudantes.
     */
    public void setOrientados(ArrayList<Estudante> orientados) {
        this.orientados = orientados;
    }

    /**
     * Gets the projects that the calling docente is involved in.
     * @return ArrayList with the projects that the docente is involved in.
     */
    public ArrayList<Project> getProjetos() {
        return this.projetos;
    }

    /**
     * Sets the projects that the docente is involved in.
     * @param projetos ArrayList with the projects that the docente is involved in.
     */
    public void setProjetos(ArrayList<Project> projetos) {
        this.projetos = projetos;
    }
}