/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TablesGetSetters;

/**
 *
 * @author hugogabriel
 */
public class ProdutosOutros {

    private String ID;
    private String Descricao;
    private String Serial;
    private String Marca;
    private String Preco;
    private String Qtde;

    
    public ProdutosOutros(){
        
    }
    
    public ProdutosOutros(String ID,String Descricao, String Serial, String Marca, String Preco, String Qtde) {
        this.ID = ID;
        this.Descricao = Descricao;
        this.Serial = Serial;
        this.Marca = Marca;
        this.Preco = Preco;
        this.Qtde = Qtde;
    }

    public String getID() {
        return ID;
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

    public String getPreco() {
        return Preco;
    }

    public void setPreco(String Preco) {
        this.Preco = Preco;
    }

    public String getQtde() {
        return Qtde;
    }

    public void setQtde(String Qtde) {
        this.Qtde = Qtde;
    }

    

}
