package com.jadventure.game.repository;

import java.io.File;

public abstract class AbstractRepository {
    private final String defaultFileName;
    private final File repoPath;


    public AbstractRepository(File repoPath, String defaultFileName) {
        this.defaultFileName = defaultFileName;

        if (repoPath == null) {
            throw new NullPointerException("Argument 'repoPath' should not be null.");
        }
        File repoFile;
        if (repoPath.isDirectory()) {
            repoFile = new File(repoPath, defaultFileName);
        }
        else {
            repoFile = repoPath;
        }
        
        System.out.println("AbstractRepository  repo path : file  " + repoPath + "\n  " + repoFile);
        if (! repoFile.isFile() || (! repoFile.canRead())) {
            if (! repoFile.isFile()) {
                throw new RepositoryException("No file found at '" + repoFile + "'.");
            }
            else {
                throw new RepositoryException("Unable to read file '" + repoFile + "'.");
            }
        }
        this.repoPath = repoFile;
    }

    protected void load() {
        load(getRepoPath());
    }
    abstract protected void load(File repo);


    public String getDefaultFileName() {
        return defaultFileName;
    }


    public File getRepoPath() {
        return repoPath;
    }

}