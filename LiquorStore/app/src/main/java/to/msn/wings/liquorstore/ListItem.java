package to.msn.wings.liquorstore;

public class ListItem {
    private  long id; //List Viewに使用するid
    private int bId ;
    private String bName;
    private int bPrice;

    private int o_id;
    private int o_m_id;
    private  int o_b_id;
    private  int O_quantity;
    private String o_data;

    public int getO_b_id() {
        return o_b_id;
    }

    public void setO_b_id(int o_b_id) {
        this.o_b_id = o_b_id;
    }

    public int getO_m_id() {
        return o_m_id;
    }

    public void setO_m_id(int o_m_id) {
        this.o_m_id = o_m_id;
    }

    public String getO_data() {
        return o_data;
    }

    public void setO_data(String o_data) {
        this.o_data = o_data;
    }

    public int getO_id() {
        return o_id;
    }

    public void setO_id(int o_id) {
        this.o_id = o_id;
    }

    public int getO_quantity() {
        return O_quantity;
    }

    public void setO_quantity(int o_quantity) {
        O_quantity = o_quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public int getbPrice() {
        return bPrice;
    }

    public void setbPrice(int bPrice) {
        this.bPrice = bPrice;
    }

}
