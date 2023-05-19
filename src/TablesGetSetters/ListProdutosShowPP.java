/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TablesGetSetters;

/**
 *
 * @author hugogabriel
 */
public class ListProdutosShowPP {

    private String Id;
    private String Codigob;
    private String Descricao;
    private String Ref;
    private String Subgrupo;
    private String Marca;
    private String Serial;
    private String Preco;
    private String Promocao;
    private int Qtde;

    public ListProdutosShowPP() {

    }

    public ListProdutosShowPP(String Id, String Codigob, String Descricao, String Ref, String Subgrupo, String Marca, String Serial, String Preco,String Promocao, int Qtde) {
        this.Id = Id;
        this.Codigob = Codigob;
        this.Descricao = Descricao;
        this.Ref = Ref;
        this.Subgrupo = Subgrupo;
        this.Marca = Marca;
        this.Serial = Serial;
        this.Preco = Preco;
        this.Promocao = Promocao;
        this.Qtde = Qtde;
    }

    public String getPromocao() {
        return Promocao;
    }

    public void setPromocao(String Promocao) {
        this.Promocao = Promocao;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getCodigob() {
        return Codigob;
    }

    public void setCodigob(String Codigob) {
        this.Codigob = Codigob;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public String getRef() {
        return Ref;
    }

    public void setRef(String Ref) {
        this.Ref = Ref;
    }

    public String getSubgrupo() {
        return Subgrupo;
    }

    public void setSubgrupo(String Subgrupo) {
        this.Subgrupo = Subgrupo;
    }

    public String getMarca() {
        return Marca;
    }

    public void setMarca(String Marca) {
        this.Marca = Marca;
    }

    public String getSerial() {
        return Serial;
    }

    public void setSerial(String Serial) {
        this.Serial = Serial;
    }

    public String getPreco() {
        return Preco;
    }

    public void setPreco(String Preco) {
        this.Preco = Preco;
    }

    public int getQtde() {
        return Qtde;
    }

    public void setQtde(int Qtde) {
        this.Qtde = Qtde;
    }

}
