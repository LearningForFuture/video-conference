package com.videoconference.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MeetingStorage {
    private static MeetingStorage instance;
    private final Map<String, Set<String>> meetings;

    private MeetingStorage() {
        meetings = new HashMap<String, Set<String>>();
    }

    public static synchronized MeetingStorage getInstance() {
        if (instance == null) {
            instance = new MeetingStorage();
        }
        return instance;
    }

    public Map<String, Set<String>> getMeetings() {
        return meetings;
    }

    public void setMeetings(String meetingId) throws Exception {
        if (meetings.containsKey(meetingId)) {
            throw new Exception("meeting id already exists with id: " + meetingId);
        }
        meetings.put(meetingId, new HashSet<>());
    }
}
