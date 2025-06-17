package com.learning.journalApp.service;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    // method to create a new journal entry
    public void createJournalEntry(JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            log.error("Exception in creating new journal entry :: ", e);
        }
    }

    // method to get all journal entries
    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    // method to get journal entry by Id
    public JournalEntry getEntryById(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    // method to delete journal entry by id
    public void deleteEntryById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }

    // method to update an existing journal entry by id
    public void updateEntryById(JournalEntry newEntry){
        journalEntryRepository.save(newEntry);
    }



}
