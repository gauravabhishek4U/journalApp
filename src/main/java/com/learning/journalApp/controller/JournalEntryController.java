package com.learning.journalApp.controller;

import com.learning.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllJournals(){
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalById(@PathVariable long myId){
        return journalEntries.get(myId);
    }

    @PostMapping
    public boolean createJournal(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @DeleteMapping("/deleteEntryById/{myId}")
    public JournalEntry deleteJournalById(@PathVariable long myId){
        return journalEntries.remove(myId);
    }

    @PutMapping("/updateEntry/{myId}")
    public JournalEntry updateJournalById(@PathVariable long myId, @RequestBody JournalEntry journalEntry){
        return journalEntries.put(myId, journalEntry);
    }
}
