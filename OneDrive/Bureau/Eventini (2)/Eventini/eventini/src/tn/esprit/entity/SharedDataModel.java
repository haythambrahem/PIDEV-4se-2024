/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entity;

/**
 *
 * @author fadi saidi
 */
public class SharedDataModel {
  private String labelValue;

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String value) {
        this.labelValue = value;
    }

    public SharedDataModel(String labelValue) {
        this.labelValue = labelValue;
    }

    public SharedDataModel() {
    }
    
}
