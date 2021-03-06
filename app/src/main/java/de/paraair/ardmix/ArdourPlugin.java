package de.paraair.ardmix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by onkel on 20.10.16.
 */

public  class ArdourPlugin {
    private int trackId;
    private int pluginId;
    public boolean enabled = false;

    private String name;

    private TreeMap<Integer, InputParameter> parameterList = new TreeMap<>();

    public ArdourPlugin() {}

    public ArdourPlugin(int trackId, int pluginId, int enabled) {
        this.trackId = trackId;
        this.pluginId = pluginId;
        this.enabled = enabled == 1 ? true : false;
    }

    public InputParameter addParameter(int index, InputParameter parameter) {
        parameterList.put(index, parameter);
        return parameter;
    }

    public String getName() {
        return name;
    }

    public TreeMap<Integer, InputParameter> getParameters() {
        return parameterList;
    }

    public InputParameter getParameter(int pi) {
        return parameterList.get(pi);
    }

    public int getTrackId() {
        return trackId;
    }

    public int getPluginId() {
        return pluginId;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static  class InputParameter {
        int parameter_index;
        String name;

        String type;
        int flags;
        float min;
        float max;
        double current;
        String print_fmt;
        int scaleSize;

        SortedMap<Float, String> scale_points = new TreeMap<>();


        public InputParameter(int index, String name) {
            parameter_index = index;
            this.name = name;
        }

        public int getFaderFromCurrent(int base) {

            float Range = max - min;
            return (int)(base / Range * (current - min));
        }

        public void setCurrentFromFader(int val, int base) {
            float range = max - min;

            if( (flags & 0x1) == 0x1 )
                current = Math.round((float)(range * val) / base + min);
            else
                current = (float)(range * val) / base + min;

        }

        public String getTextFromCurrent() {
            if( print_fmt == null || print_fmt.isEmpty() ) {
                if( (flags & 0x02) == 0x02)
                    return String.format("%.0f", current);
                else
                    return String.format("%.2f", current);
            }
            else
                return String.format(print_fmt, current);
        }

        public int getIndexFromScalePointKey(int key) {
            int index = 0;

            for(Map.Entry<Float,String> entry : scale_points.entrySet()) {
                if( entry.getKey() == key )
                    return index;
                index++;
            }
            return 0;
        }

        public void addScalePoint(float val, String name) {
            scale_points.put(val, name);
        }
    }


}
