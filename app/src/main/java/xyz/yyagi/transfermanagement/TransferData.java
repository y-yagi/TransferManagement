package xyz.yyagi.transfermanagement;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by yaginuma on 14/12/25.
 */
public class TransferData extends SugarRecord<TransferData> {
    String type;
    String year;
    String month;

    public TransferData() { }

    public TransferData(String type, String year, String month) {
        this.type = type;
        this.year = year;
        this.month = month;
    }

    public static boolean isExist(String type, String year, String month) {
        List<TransferData> list =
                TransferData.find(TransferData.class, "type= ? and year = ? and month = ?", type, year, month);
        if (list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return type + " : " + year + "年" + month + "月分";
    }

}
