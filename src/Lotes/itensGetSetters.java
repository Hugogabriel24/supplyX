/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lotes;

/**
 *
 * @author hugog
 */
public class itensGetSetters {

    private String tipo;
    private String descricao;
    private String serial;
    private String marca;
    private String modelo;
    private String patrimonio;
    private String status;
    private String preco;
    private String quantidade;
    private String obs;

    public itensGetSetters() {

    }

    public itensGetSetters(String tipo, String descricao, String serial, String marca, String modelo, String patrimonio, String status, String preco, String quantidade,String obs) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.serial = serial;
        this.marca = marca;
        this.modelo = modelo;
        this.patrimonio = patrimonio;
        this.status = status;
        this.preco = preco;
        this.quantidade = quantidade;
        this.obs = obs;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPatrimonio() {
        return patrimonio;
    }

    public void setPatrimonio(String patrimonio) {
        this.patrimonio = patrimonio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    

}
