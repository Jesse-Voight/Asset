/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wdhb.util;

/**
 *
 * @author JessVoig
 */
public class PCObject {
    public PCObject(String idPC,String name,String idLocation,String idPCModel,String SerialNo,String Monitor1,String Monitor2,String AssetNo,String notes,String status,String commusers){
    }
    
    String idPC,name,idLocation,idPCModel,SerialNo,Monitor1,Monitor2,AssetNo,notes,status,commusers;
    String friendlyBuilding;
    String friendlyDepartment;

    public String getIdPC() {
        return idPC;
    }

    public void setIdPC(String idPC) {
        this.idPC = idPC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(String idLocation) {
        this.idLocation = idLocation;
    }

    public String getIdPCModel() {
        return idPCModel;
    }

    public void setIdPCModel(String idPCModel) {
        this.idPCModel = idPCModel;
    }

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.SerialNo = SerialNo;
    }

    public String getMonitor1() {
        return Monitor1;
    }

    public void setMonitor1(String Monitor1) {
        this.Monitor1 = Monitor1;
    }

    public String getMonitor2() {
        return Monitor2;
    }

    public void setMonitor2(String Monitor2) {
        this.Monitor2 = Monitor2;
    }

    public String getAssetNo() {
        return AssetNo;
    }

    public void setAssetNo(String AssetNo) {
        this.AssetNo = AssetNo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommusers() {
        return commusers;
    }

    public void setCommusers(String commusers) {
        this.commusers = commusers;
    }
    
}
