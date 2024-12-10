package util;

import java.io.Serializable;

public class EgtoCh implements Serializable {
    private static final long serialVersionUID = 1L;

    public String total;
    public String English;
    public String correctChinese;
    public String[] wrongChinese = new String[3];
}
