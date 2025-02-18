package to.msn.wings.liquorstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderListadapter extends BaseAdapter {
    private Context context;
    private ArrayList<ListItem> data;
    private  int resource;
    private LayoutInflater inflater;

    public OrderListadapter(Context context, ArrayList<ListItem> data,
                           int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
        this.inflater = (LayoutInflater) this.context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem item = (ListItem) getItem(position);
        View sview = (convertView != null) ? convertView : inflater.inflate(resource, null);
        ((TextView) sview.findViewById(R.id.liName)).setText(item.getbName());
        String pri = String.format(item.getbPrice() + "円");
        ((TextView) sview.findViewById(R.id.liPrice)).setText(pri);
        String qua = item.getO_quantity() == 0 ? "" : String.format(item.getO_quantity() + "個");
        ((TextView) sview.findViewById(R.id.liQuantity)).setText(qua);
        String sum = item.getbPrice()*item.getO_quantity() == 0 ? "" : "小計" + (item.getbPrice()*item.getO_quantity()) + "円";
        ((TextView) sview.findViewById(R.id.liSum)).setText(sum);
        ((TextView) sview.findViewById(R.id.tvdata)).setText(item.getO_data());
        return sview;
    }

}
