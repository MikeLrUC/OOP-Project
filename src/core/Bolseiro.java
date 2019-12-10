package core;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Represents a Bolseiro.
 */
abstract class Bolseiro extends Pessoa {

    protected Calendar inicoBolsa;
    protected Calendar fimBolsa;
    protected Project projeto;

    /**
     * Creates a Bolseiro with his name, email, research center, beginning of the scholarship and end of the scholarship.
     * @param nome String with the bolseiro's name.
     * @param email String with the bolseiro's email.
     * @param researchCenter ResearchCenter objetct with the bolseiro's research center.
     * @param inicioBolsa Calendat with the starting date of the bolseiro's scholarship.
     * @param fimBolsa Calendar with the ending date of the bolseiro's scholarship.
     */
    public Bolseiro(String nome, String email, ResearchCenter researchCenter,Calendar inicioBolsa, Calendar fimBolsa){
        super(nome, email, researchCenter);
        this.inicoBolsa = inicioBolsa;
        this.fimBolsa = fimBolsa;
    }

    /**
     *Gets the Bolseiro's cost in a month.
     * @return int with the cost of the calling Bolseiro in a month.
     */
    public abstract int getCusto();

    /**
     * Gets the starting date of the bolseiro's scholarship.
     * @return Calendar with the starting date of the bolseiro's scholarship.
     */
    public Calendar getInicoBolsa() {
        return this.inicoBolsa;
    }

    /**
     * Sets the starting date of the bolseiro's scholarship.
     * @param inicoBolsa Calendar with the starting date of the bolseiro's scholarship.
     */
    public void setInicoBolsa(Calendar inicoBolsa) {
        this.inicoBolsa = inicoBolsa;
    }

    /**
     * Gets the ending date of the bolseiro's scholarship.
     * @return Calendar with the ending date of the bolseiro's scholarship.
     */
    public Calendar getFimBolsa() {
        return this.fimBolsa;
    }

    /**
     * Sets the ending date of the bolseiro's scholarship.
     * @param fimBolsa Calendar with the ending date of the bolseiro's scholarship.
     */
    public void setFimBolsa(Calendar fimBolsa) {
        this.fimBolsa = fimBolsa;
    }

    /**
     * Gets the bolseiro's project.
     * @return Project with the bolseiro's project.
     */
    public Project getProjeto() {
        return this.projeto;
    }

    /**
     * Sets the bolseiro's project.
     * @param projetos Project with the bolseiro's project.
     */
    public void setProjeto(Project projetos) {
        this.projeto = projetos;
    }
}