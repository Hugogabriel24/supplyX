package pagesEstoque;

import br.com.tanamao.dal.ModuloConexao;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import notification.Notification;
import static pagesEstoque.TelaEstoquePP.tablePrint;

public final class printpp extends javax.swing.JFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    Connection conexao1 = null;
    PreparedStatement pst1 = null;
    ResultSet rs1 = null;
    Connection conexao2 = null;
    PreparedStatement pst2 = null;
    ResultSet rs2 = null;

    public printpp() {
        initComponents();
        conexao = ModuloConexao.conector();
        conexao1 = ModuloConexao.conector();
        conexao2 = ModuloConexao.conector();
        setimgEtiquetas();
        setIcon();
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/src/mark 2.7.png")));
    }

    public void setimgEtiquetas() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/src/etiqueta2x.png"));
        icon.setImage(icon.getImage().getScaledInstance(420, 120, 100));
        img1.setIcon(icon);

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/src/etiqueta3x.png"));
        icon1.setImage(icon1.getImage().getScaledInstance(420, 120, 100));
        img2.setIcon(icon1);

        ImageIcon icon2 = new ImageIcon(getClass().getResource("/src/etiqueta2x.png"));
        icon2.setImage(icon2.getImage().getScaledInstance(420, 120, 100));
        img3.setIcon(icon2);

    }

    public void print1x(String codabar1, String ref1) {

        if (ref1.length() >= 15) {
            String[] partes = ref1.split("/");
            FileOutputStream os = null;
            String porta = "LPT1";
            try {

                os = new FileOutputStream(porta);
                PrintStream ps = new PrintStream(os);

                ps.println("^CT~~CD,~CC^~CT~");
                ps.println("^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR4,4~SD30^JUS^LRN^CI0^XZ");
                ps.println("^XA");
                ps.println("^MMT");
                ps.println("^PW831");
                ps.println("^LL0216");
                ps.println("^LS0");
                ps.println("^FO740,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^BY2,2,71^FT786,38^BEI,,Y,N");
                ps.println("^FD" + codabar1 + "^FS");
                ps.println("^FT753,144^A0I,20,21^FH\\^FD" + partes[0] + "^FS");
                ps.println("^FT753,120^A0I,20,21^FH\\^FD/" + partes[1] + "^FS");
                ps.println("^PQ1,0,1,Y^XZ");
                ps.close();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Etiqueta impressa com sucesso!");
                noti.showNotification();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível enviar os dados para impressão\nA porta \"" + porta + "\" parece estar inacessível\n" + e.getMessage(), "Erro ao enviar os dados para a porta", JOptionPane.ERROR_MESSAGE);
            }
        } else {

            FileOutputStream os = null;
            String porta = "LPT1";
            try {
                os = new FileOutputStream(porta);
                PrintStream ps = new PrintStream(os);

                ps.println("^CT~~CD,~CC^~CT~");
                ps.println("^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR4,4~SD30^JUS^LRN^CI0^XZ");
                ps.println("^XA");
                ps.println("^MMT");
                ps.println("^PW831");
                ps.println("^LL0216");
                ps.println("^LS0");
                ps.println("^FO740,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^BY2,2,71^FT786,38^BEI,,Y,N");
                ps.println("^FD" + codabar1 + "^FS");
                ps.println("^FT753,144^A0I,20,21^FH\\^FD" + ref1 + "^FS");
                ps.println("^PQ1,0,1,Y^XZ");
                ps.close();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Etiqueta impressa com sucesso!");
                noti.showNotification();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível enviar os dados para impressão\nA porta \"" + porta + "\" parece estar inacessível\n" + e.getMessage(), "Erro ao enviar os dados para a porta", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public void print2x(String codabar1, String ref1, String codabar2, String ref2) {

        if (ref1.length() >= 15 || ref2.length() >= 15) {
            String[] partes1 = ref1.split("/");
            String[] partes2 = ref2.split("/");
            FileOutputStream os = null;
            String porta = "LPT1";
            try {
                os = new FileOutputStream(porta);
                PrintStream ps = new PrintStream(os);
                ps.println("^CT~~CD,~CC^~CT~");
                ps.println("^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR4,4~SD30^JUS^LRN^CI0^XZ");
                ps.println("^XA");
                ps.println("^MMT");
                ps.println("^PW831");
                ps.println("^LL0216");
                ps.println("^LS0");
                ps.println("^FO740,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^FO448,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^BY2,2,71^FT786,38^BEI,,Y,N");
                ps.println("^FD" + codabar1 + "^FS");
                ps.println("^FT753,144^A0I,20,21^FH\\^FD" + partes1[0] + "^FS");
                ps.println("^FT753,120^A0I,20,21^FH\\^FD/" + partes1[1] + "^FS");
                ps.println("^BY2,2,71^FT478,38^BEI,,Y,N");
                ps.println("^FD" + codabar2 + "^FS");
                ps.println("^FT465,144^A0I,20,21^FH\\^FD" + partes2[0] + "^FS");
                ps.println("^FT465,120^A0I,20,21^FH\\^FD/" + partes2[1] + "^FS");
                ps.println("^PQ1,0,1,Y^XZ");

                ps.close();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Etiqueta impressa com sucesso!");
                noti.showNotification();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível enviar os dados para impressão\nA porta \"" + porta + "\" parece estar inacessível\n" + e.getMessage(), "Erro ao enviar os dados para a porta", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            FileOutputStream os = null;
            String porta = "LPT1";
            try {
                os = new FileOutputStream(porta);
                PrintStream ps = new PrintStream(os);
                ps.println("^CT~~CD,~CC^~CT~");
                ps.println("^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR4,4~SD30^JUS^LRN^CI0^XZ");
                ps.println("^XA");
                ps.println("^MMT");
                ps.println("^PW831");
                ps.println("^LL0216");
                ps.println("^LS0");
                ps.println("^FO740,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^FO448,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^BY2,2,71^FT786,38^BEI,,Y,N");
                ps.println("^FD" + codabar1 + "^FS");
                ps.println("^FT753,144^A0I,20,21^FH\\^FD" + ref1 + "^FS");
                ps.println("^BY2,2,71^FT478,38^BEI,,Y,N");
                ps.println("^FD" + codabar2 + "^FS");
                ps.println("^FT465,144^A0I,20,21^FH\\^FD" + ref2 + "^FS");
                ps.println("^PQ1,0,1,Y^XZ");

                ps.close();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Etiqueta impressa com sucesso!");
                noti.showNotification();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível enviar os dados para impressão\nA porta \"" + porta + "\" parece estar inacessível\n" + e.getMessage(), "Erro ao enviar os dados para a porta", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void print3x(String codabar1, String ref1, String codabar2, String ref2, String codabar3, String ref3) {
        
        if (ref1.length() >= 15 || ref2.length() >= 15 || ref3.length() >= 15) {
            String[] partes1 = ref1.split("/");
            String[] partes2 = ref2.split("/");
            String[] partes3 = ref3.split("/");
            FileOutputStream os = null;
            String porta = "LPT1";
            try {
                os = new FileOutputStream(porta);
                PrintStream ps = new PrintStream(os);
                ps.println("^CT~~CD,~CC^~CT~");
                ps.println("^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR4,4~SD10^JUS^LRN^CI0^XZ");
                ps.println("^XA");
                ps.println("^MMT");
                ps.println("^PW831");
                ps.println("^LL0216");
                ps.println("^LS0");
                ps.println("^FO740,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^FO448,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^FO155,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkjFuwjAUhn/Xgwfa+gIW3ll6AhIOFolUjEg5AkeBdMolGKIyMFQq6dQMkU0inp99ASZ406df7/22/2fgEWtRRl6vGKVvmZXvmPO9i+1mHfDFZR9hQHSFCgP6x8me2KoBBXGmf5ETF9ZAEfduhxnd6K8zkPWNT20GeTtANJ8DBOmXreOeRh1ZP+sq6KIxJurVnPvPr4n+nvibZfSvfMn+b76O/pee/W1dsH4oD5vAA9RX4B7yRP7/NYQn3Y9vDTxMGRFPIWvKYcrMUj75qO1JtytwnroF5yw6cP6jUV4GtuB9QX/HPab7TfeetI//BM+6b10BgKpkYw==:1D20");
                ps.println("^BY2,2,71^FT786,38^BEI,,Y,N");
                ps.println("^FD" + codabar1 + "^FS");
                ps.println("^FT763,144^A0I,20,21^FH\\^FD" + partes1[0] + "^FS");
                ps.println("^FT763,120^A0I,20,21^FH\\^FD/" + partes1[1] + "^FS");
                ps.println("^BY2,2,71^FT478,38^BEI,,Y,N");
                ps.println("^FD" + codabar2 + "^FS");
                ps.println("^FT475,144^A0I,20,21^FH\\^FD" + partes2[0] + "^FS");
                ps.println("^FT475,120^A0I,20,21^FH\\^FD/" + partes2[1] + "^FS");
                ps.println("^BY2,2,71^FT201,38^BEI,,Y,N");
                ps.println("^FD" + codabar3 + "^FS");
                ps.println("^FT178,144^A0I,20,21^FH\\^FD" + partes3[0] + "^FS");
                ps.println("^FT178,120^A0I,20,21^FH\\^FD/" + partes3[1] + "^FS");
                ps.println("^PQ1,0,1,Y^XZ");
                ps.close();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Etiqueta impressa com sucesso!");
                noti.showNotification();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível enviar os dados para impressão\nA porta \"" + porta + "\" parece estar inacessível\n" + e.getMessage(), "Erro ao enviar os dados para a porta", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            FileOutputStream os = null;
            String porta = "LPT1";
            try {
                os = new FileOutputStream(porta);
                PrintStream ps = new PrintStream(os);
                ps.println("^CT~~CD,~CC^~CT~");
                ps.println("^XA~TA000~JSN^LT0^MNW^MTT^PON^PMN^LH0,0^JMA^PR4,4~SD10^JUS^LRN^CI0^XZ");
                ps.println("^XA");
                ps.println("^MMT");
                ps.println("^PW831");
                ps.println("^LL0216");
                ps.println("^LS0");
                ps.println("^FO740,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^FO448,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkTFqxDAQRb8Ri7qo3EAcdA0Vi/cSSZ17BG8kSO9cJ6WbsJ23TBNYwx4gTqfCWWWNZ0a+QKpkQPD4+vo2f4C/OPcLPgdBnVphk3phvxuz3Z0ZV2P9wA9UHw0/sO+jHogrExGJa/uBJ+JYOVzNWHx9b7AmPvUOev6jYt/W0PMH1IuKUKQf16N4GvMqemdL1lXjnOiHciP+7jrrXZnzG/eY829Tzr9JIecfB8nfhij5vvDsP0SYZ+YBek/5nwEqkZ6mQzx1syWeSrbcz9Qdsb/c7bjPAOnTtpCeVQ/p/xLkwVNB9gX7lve43O9y7ws7cIf/+d35AbH/XTE=:C4B5");
                ps.println("^FO155,96^GFA,01152,01152,00012,:Z64:");
                ps.println("eJztkjFuwjAUhn/Xgwfa+gIW3ll6AhIOFolUjEg5AkeBdMolGKIyMFQq6dQMkU0inp99ASZ406df7/22/2fgEWtRRl6vGKVvmZXvmPO9i+1mHfDFZR9hQHSFCgP6x8me2KoBBXGmf5ETF9ZAEfduhxnd6K8zkPWNT20GeTtANJ8DBOmXreOeRh1ZP+sq6KIxJurVnPvPr4n+nvibZfSvfMn+b76O/pee/W1dsH4oD5vAA9RX4B7yRP7/NYQn3Y9vDTxMGRFPIWvKYcrMUj75qO1JtytwnroF5yw6cP6jUV4GtuB9QX/HPab7TfeetI//BM+6b10BgKpkYw==:1D20");
                ps.println("^BY2,2,71^FT756,38^BEI,,Y,N");
                ps.println("^FD" + codabar1 + "^FS");
                ps.println("^FT763,144^A0I,20,21^FH\\^FD" + ref1 + "^FS");
                ps.println("^BY2,2,71^FT478,38^BEI,,Y,N");
                ps.println("^FD" + codabar2 + "^FS");
                ps.println("^FT475,144^A0I,20,21^FH\\^FD" + ref2 + "^FS");
                ps.println("^BY2,2,71^FT181,38^BEI,,Y,N");
                ps.println("^FD" + codabar3 + "^FS");
                ps.println("^FT178,144^A0I,20,21^FH\\^FD" + ref3 + "^FS");
                ps.println("^PQ1,0,1,Y^XZ");
                ps.close();
                Notification noti = new Notification(this, Notification.Type.SUCCESS, Notification.Location.TOP_CENTER, "Etiqueta impressa com sucesso!");
                noti.showNotification();
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Não foi possível enviar os dados para impressão\nA porta \"" + porta + "\" parece estar inacessível\n" + e.getMessage(), "Erro ao enviar os dados para a porta", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btPrinter = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        etiqueta1 = new javax.swing.JCheckBox();
        etiqueta3 = new javax.swing.JCheckBox();
        etiqueta2 = new javax.swing.JCheckBox();
        img1 = new javax.swing.JLabel();
        img2 = new javax.swing.JLabel();
        img3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Selecionar a etiqueta para impressão");
        setResizable(false);

        btPrinter.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        btPrinter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/src/imprimir.png"))); // NOI18N
        btPrinter.setText("Imprimir etiqueta");
        btPrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPrinterActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel20.setText("Selecione o modelo da etiqueta :");

        buttonGroup1.add(etiqueta1);
        etiqueta1.setText("Etiqueta 3X (3.1cm x 2.1cm)");
        etiqueta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etiqueta1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(etiqueta3);
        etiqueta3.setText("Etiqueta 3x (3.1cm x 2.1cm)");

        buttonGroup1.add(etiqueta2);
        etiqueta2.setText("Etiqueta 2x (5.0cm x 3.0cm)");
        etiqueta2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etiqueta2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(img1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(etiqueta1)
                            .addComponent(etiqueta3)
                            .addComponent(etiqueta2))
                        .addGap(0, 206, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(img3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(9, 9, 9))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btPrinter)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(img2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(etiqueta1)
                .addGap(147, 147, 147)
                .addComponent(etiqueta2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(img1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(etiqueta3)
                .addGap(18, 18, 18)
                .addComponent(img3, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPrinter)
                .addGap(51, 51, 51))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(87, 87, 87)
                    .addComponent(img2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(411, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void etiqueta2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etiqueta2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_etiqueta2ActionPerformed

    private void btPrinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPrinterActionPerformed

        String cod = "";
        String ref = "";

        String cod1 = "";
        String cod2 = "";
        String ref1 = "";
        String ref2 = "";
        String cod3 = "";
        String ref3 = "";
        if (etiqueta1.isSelected()) {

            switch (tablePrint.getRowCount()) {
                case 1:
                    {
                        String getID = (tablePrint.getModel().getValueAt(0, 0).toString());
                        String sql = "select * from partepecas where idpartepecas like ?";
                        try {
                            pst = conexao.prepareStatement(sql);
                            pst.setString(1, getID);
                            rs = pst.executeQuery();
                            
                            while (rs.next()) {
                                cod = rs.getString(2);
                                ref = rs.getString(55);
                                
                            }
                            print1x(cod, ref);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }       break;
                    }
                case 2:
                    {
                        String getID1 = (tablePrint.getModel().getValueAt(0, 0).toString());
                        String getID2 = (tablePrint.getModel().getValueAt(1, 0).toString());
                        String sql = "select * from partepecas where idpartepecas like ?";
                        String sql1 = "select * from partepecas where idpartepecas like ?";
                        try {
                            pst = conexao.prepareStatement(sql);
                            pst.setString(1, getID1);
                            rs = pst.executeQuery();
                            
                            while (rs.next()) {
                                
                                cod1 = rs.getString(2);
                                ref1 = rs.getString(55);
                                
                            }
//                            conexao.close();
//                            pst.close();
                            
                            pst1 = conexao1.prepareStatement(sql1);
                            pst1.setString(1, getID2);
                            rs1 = pst1.executeQuery();
                            
                            while (rs1.next()) {
                                
                                cod2 = rs1.getString(2);
                                ref2 = rs1.getString(55);
                                
                            }
//                            conexao1.close();
//                            pst1.close();
                            print2x(cod1, ref1, cod2, ref2);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }       break;
                    }
                case 3:
                    {
                        String getID1 = (tablePrint.getModel().getValueAt(0, 0).toString());
                        String getID2 = (tablePrint.getModel().getValueAt(1, 0).toString());
                        String getID3 = (tablePrint.getModel().getValueAt(2, 0).toString());
                        String sql = "select * from partepecas where idpartepecas like ?";
                        String sql1 = "select * from partepecas where idpartepecas like ?";
                        String sql2 = "select * from partepecas where idpartepecas like ?";
                        try {
                            pst = conexao.prepareStatement(sql);
                            pst.setString(1, getID1);
                            rs = pst.executeQuery();
                            
                            while (rs.next()) {
                                
                                cod1 = rs.getString(2);
                                ref1 = rs.getString(55);
                                
                            }
//                            conexao.close();
//                            pst.close();
                            
                            pst1 = conexao1.prepareStatement(sql1);
                            pst1.setString(1, getID2);
                            rs1 = pst1.executeQuery();
                            
                            while (rs1.next()) {
                                
                                cod2 = rs1.getString(2);
                                ref2 = rs1.getString(55);
                                
                            }
//                            conexao1.close();
//                            pst1.close();
                            
                            pst2 = conexao2.prepareStatement(sql2);
                            pst2.setString(1, getID3);
                            rs2 = pst2.executeQuery();
                            
                            while (rs2.next()) {
                                
                                cod3 = rs2.getString(2);
                                ref3 = rs2.getString(55);
                                
                            }

                            print3x(cod1, ref1, cod2, ref2, cod3, ref3);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(null, e);
                        }       break;
                    }
                default:
                    break;
            }
        }

    }//GEN-LAST:event_btPrinterActionPerformed

    private void etiqueta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etiqueta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_etiqueta1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(printpp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(printpp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(printpp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(printpp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new printpp().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btPrinter;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox etiqueta1;
    private javax.swing.JCheckBox etiqueta2;
    private javax.swing.JCheckBox etiqueta3;
    private javax.swing.JLabel img1;
    private javax.swing.JLabel img2;
    private javax.swing.JLabel img3;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
