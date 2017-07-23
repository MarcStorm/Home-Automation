package COURSE02148.homeautomation.server.api;

public enum Intent {

    INTENT("outcomes.intent"),
    CONFIDENCE("outcomes.confidence"),
    TEXT("_text"),
    ONOFF("outcomes.entities.on_off.value"),
    DATETIME("outcomes.entities.datetime.value"),
    DATETIME_GRAIN("outcomes.entities.datetime.grain"),
    DATETIME_TYPE("outcomes.entities.datetime.type"),
    REMINDER("outcomes.entities.reminder.value"),
    REMINDER_SUGGESTED("outcomes.entities.reminder.suggested"),
    MESSAGEBODY("outcomes.entities.message_body.value"),
    LOCK("outcomes.entities.lock.value"),
    NO_FILTER(null);

    private String key;

    Intent(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }
}
