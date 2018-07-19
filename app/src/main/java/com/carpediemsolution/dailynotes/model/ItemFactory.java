package com.carpediemsolution.dailynotes.model;

import static com.carpediemsolution.dailynotes.utils.Constants.TYPE_NOTE;
import static com.carpediemsolution.dailynotes.utils.Constants.TYPE_TASK;

public class ItemFactory {

    public AbstractItem getItem(int type) {

        switch (type) {
            case TYPE_NOTE:
                return new Note();
            case TYPE_TASK:
                return new Task();
            default:
                return new Note();
        }
    }
}
