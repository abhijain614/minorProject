/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FTS.pojo;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class FileStore {
    
    @Override
    public String toString() {
        return "FileStore{" + "fileList=" + fileList + '}';
    }
     
    ArrayList<File> fileList;
    
    public FileStore(){
    fileList = new ArrayList<>();
    }
    
    public File getFile(int pos){
    return fileList.get(pos);
    }
    
    public int getCount(){
    return fileList.size();
    }
    
    public void addFile(File f){
        fileList.add(f);
    }
    
    public void addAllFiles(ArrayList<File> fList){
    fileList.addAll(fList);
    }
    
    public ArrayList<File> getAllFiles(){
    return fileList;
    }
}
