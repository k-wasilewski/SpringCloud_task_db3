package com.springcloud.task_db3;

import org.hibernate.*;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.repository.TaskExecution;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TaskListener implements TaskExecutionListener {
    Logger logger = Logger.getLogger("logger");
    FileHandler fh;

    //mysql -h 127.0.0.1 -P 3307 -u root -proot
    public void onTaskStartup(TaskExecution te) {
        try {
            fh = new FileHandler("/home/kuba/Desktop/projects/SpringCloud/task_db3.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File folder = new File(newestFolder());
        File file = null;
        try {file = folder.listFiles()[0];} catch (NullPointerException npe) {
            logger.info("Error uploading the file to the database");
        }
        FileReader fr=null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException fnfe) {logger.info("Error uploading the file to the database");}
        try {
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                Session session = HibernateUtil.getSessionFactory().openSession();

                while ((line = br.readLine()) != null) {
                    session.beginTransaction();
                    session.createSQLQuery(line).executeUpdate();
                    session.getTransaction().commit();
                }

                logger.info("Dump executed successfully");
                session.close();
            }
        } catch (IOException e) {
            logger.info("Error reading the file");
        }
    }

    public void onTaskEnd(TaskExecution te) {
        logger.info("task end");
    }

    public void onTaskFailed(TaskExecution te, Throwable t) {
        logger.info("task failed");
    }

    private String newestFolder() {
        File file = new File("/home/kuba/Desktop/projects/SpringCloud");
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        List<Integer> ints = new ArrayList<>();
        int i=0;
        for(String str:directories){
            int parsedNo;
            try {
                parsedNo=Integer.parseInt(str.trim());
                ints.add(parsedNo);
            } catch (NumberFormatException nfe) {}
            i++;
        }
        Collections.sort(ints, Collections.reverseOrder());

        int newNumber=0;
        if (!ints.isEmpty()) newNumber = ints.get(0);

        return "/home/kuba/Desktop/projects/SpringCloud/"+newNumber;
    }
}
