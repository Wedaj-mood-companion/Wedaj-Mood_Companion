package core;

private final Map<String, String> store = new HashMap<>();
if (t.startsWith("my name is ")) {
    String name = input.substring(11).trim();
    store.put("name", name);
    return "Nice to meet you, " + name + " 🙂";
}
if (t.startsWith("remember that i like ")) {
    String like = input.substring("remember that i like ".length()).trim();
    store.put("like", like);
    return "Got it. I’ll remember you like " + like + " 📝";
}
if (t.equals("what's my name?") || t.equals("whats my name?")) {
    String n = store.get("name");
    return n != null ? "You told me your name is " + n + " 🙂" : "I don’t have your name yet.";
}
if (t.equals("what do i like?")) {
    String l = store.get("like");
    return l != null ? "You like " + l + " 🌟" : "I don’t have that yet.";
}
if (t.equals("reset memory")) {
    store.clear();
    return "Memory reset 🧹";
}
return null; // not a memory command
