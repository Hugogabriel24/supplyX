/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TablesGetSetters;

/**
 *
 * @author hugogabriel
 */
public class ProdutosToPrint {

    private String ID;
    private String Descricao;
    
    public ProdutosToPrint(){
        
    }
    
    public ProdutosToPrint(String ID,String Descricao) {
        this.ID = ID;
        this.Descricao = Descricao;
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

    
    

}
