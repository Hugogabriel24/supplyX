
package TablesGetSetters;

public class ProdutosToPrintBox {

    private String ID;
    private String Caixa;

    public ProdutosToPrintBox() {

    }

    public ProdutosToPrintBox(String ID, String Caixa) {
        this.ID = ID;
        this.Caixa = Caixa;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCaixa() {
        return Caixa;
    }

    public void setCaixa(String Caixa) {
        this.Caixa = Caixa;
    }

}
