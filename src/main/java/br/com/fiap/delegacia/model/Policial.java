package br.com.fiap.delegacia.model;

public class Policial {

    private Long id;
    private String nome;
    private String cpf;
    private String matricula;
    private String cargo;
    private Long delegaciaId;

    public Policial() {
    }

    public Policial(String nome, String cpf, String matricula,
                    String cargo, Long delegaciaId) {
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.cargo = cargo;
        this.delegaciaId = delegaciaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getDelegaciaId() {
        return delegaciaId;
    }

    public void setDelegaciaId(Long delegaciaId) {
        this.delegaciaId = delegaciaId;
    }

    @Override
    public String toString() {
        return "Policial{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", matricula='" + matricula + '\'' +
                ", cargo='" + cargo + '\'' +
                ", delegaciaId=" + delegaciaId +
                '}';
    }
}
