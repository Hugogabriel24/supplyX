/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TablesGetSetters;

/**
 *
 * @author hugogabriel
 */
public class ProdutosToUsage {

    private String ID;
    private String Produto;
    private String Descricao;
    private String Codb;
    private String Serial;
    private String Marca;
    private String Qtde;

    public ProdutosToUsage() {

    }

    public ProdutosToUsage(String ID, String Produto, String Descricao, String Codb, String Serial, String Marca, String Qtde) {
        this.ID = ID;
        this.Produto = Produto;
        this.Descricao = Descricao;
        this.Codb = Codb;
        this.Serial = Serial;
        this.Marca = Marca;
        this.Qtde = Qtde;
    }

    public String getCodb() {
        return Codb;
    }

    public void setCodb(String Codb) {
        this.Codb = Codb;
    }

    public String getID() {
        return ID;
    }

    public String getProduto() {
        return Produto;
    }

    public void setProduto(String Produto) {
        this.Produto = Produto;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String Serial) {
        this.Serial = Serial;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getQtde() {
        return Qtde;
    }

    public void setQtde(String Qtde) {
        this.Qtde = Qtde;
    }

}
