package com.mike.client.frontend.till.tillMenu.barTabMenu;

import com.mike.client.frontend.Pair;
import com.mike.message.Table;

import javax.swing.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike on 13/09/2016.
 */
public class BarTabMenuModel {

    private Integer chosenTab = null;

    public Map<Integer, Table.TableStatus> getCurrentStatuses() {
        return currentStatuses;
    }

    public enum Functionality {GET_TAB, ADD_TO_TAB};

    private Functionality functionality = Functionality.GET_TAB;
    public int numberOfTabs;
    private boolean tabReceived;
    private Map<Integer, Table.TableStatus> currentStatuses;

    public BarTabMenuModel() {}

    public void init(Map<Integer, Table.TableStatus> statusMap, Functionality functionality) {
        Map<Integer, Table.TableStatus> filteredStatusMap = filterNonFreeTables(statusMap);
        this.setCurrentStatuses(filteredStatusMap);
        this.numberOfTabs = filteredStatusMap.size();
        this.setFunctionality(functionality);
    }

    public Functionality getState()
    {
        return getFunctionality();
    }
    // inner class to compare buttons
    class ValueComparator implements Comparator<JButton> {

        Map<JButton, Pair<Integer, Table.TableStatus>> base;
        public ValueComparator(Map<JButton, Pair<Integer, Table.TableStatus>> base) {
            this.base = base;
        }

        @Override
        public int compare(JButton a, JButton b) {
            if (base.get(a).getFirst() < base.get(b).getFirst()) {
                return -1;
            } else {
                return 1;
            } // returning 0 would merge keys
        }
    }

    private Map<Integer, Table.TableStatus> filterNonFreeTables(Map<Integer, Table.TableStatus> tableStatusMap) {
        Map<Integer, Table.TableStatus> mapToReturn = new HashMap<>();
        for (Integer i : tableStatusMap.keySet()) {
            if (tableStatusMap.get(i) != Table.TableStatus.FREE && tableStatusMap.get(i) != Table.TableStatus.DIRTY) {
                mapToReturn.put(i, tableStatusMap.get(i));
            }
        }
            return mapToReturn;
    }

    public void setCurrentStatuses(Map<Integer, Table.TableStatus> currentStatuses) {this.currentStatuses = currentStatuses; }
    public Integer getChosenTab() { return chosenTab; }
    public void setChosenTab(Integer chosenTab) { this.chosenTab = chosenTab; }
    public Functionality getFunctionality() { return functionality; }
    public void setFunctionality(Functionality functionality) { this.functionality = functionality; }
}
