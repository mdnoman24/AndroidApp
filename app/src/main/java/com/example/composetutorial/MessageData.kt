package com.example.composetutorial

data class Message(
    val id: String,
    val author: String,
    val body: String,
    val profilePicture: Int
)

data class Person(
    val id: String,
    val name: String,
    val status: String,
    val profilePicture: Int
)
object SampleData {
    val people = listOf(
        Person("1", "John", "Online", R.drawable.john),
        Person("2", "Smith", "Offline", R.drawable.smith),
        Person("3", "Charlie", "Away", R.drawable.buddies),
        Person("4", "Angel", "Online", R.drawable.person1),
        Person("5", "Demon", "Offline", R.drawable.person2),
        Person("6", "Devil", "Away", R.drawable.person3)
    )

    val conversationSample = listOf(
        // Messages for John
        Message("1", "John", "Hey John! How’s it going?", R.drawable.john),
        Message("1", "John", "Are you free this weekend?", R.drawable.john),
        Message("1", "John", "Let’s catch up soon.", R.drawable.john),
        Message("1", "John", "Did you finish the project we talked about?", R.drawable.john),
        Message("1", "John", "I saw something today that reminded me of you.", R.drawable.john),
        Message("1", "John", "Do you have any updates on your travel plans?", R.drawable.john),
        Message("1", "John", "What’s been your favorite moment this week?", R.drawable.john),
        Message("1", "John", "Let’s plan a game night!", R.drawable.john),
        Message("1", "John", "I really value our friendship, John.", R.drawable.john),
        Message("1", "John", "Stay awesome! Let’s talk soon.", R.drawable.john),

        // Messages for Smith
        Message("2", "Smith", "Hi Smith! How are things?", R.drawable.smith),
        Message("2", "Smith", "When can we meet up again?", R.drawable.smith),
        Message("2", "Smith", "I really enjoyed our last conversation.", R.drawable.smith),
        Message("2", "Smith", "Have you been working on anything exciting?", R.drawable.smith),
        Message("2", "Smith", "Can you share the book recommendation you mentioned?", R.drawable.smith),
        Message("2", "Smith", "How’s your family doing?", R.drawable.smith),
        Message("2", "Smith", "Let me know if you need any help with your project.", R.drawable.smith),
        Message("2", "Smith", "What’s your favorite way to spend a weekend?", R.drawable.smith),
        Message("2", "Smith", "Thanks for always being there for me.", R.drawable.smith),
        Message("2", "Smith", "Looking forward to hearing from you soon.", R.drawable.smith),

        // Messages for Charlie
        Message("3", "Charlie", "Hey Charlie, what’s up?", R.drawable.buddies),
        Message("3", "Charlie", "Do you have plans for the holidays?", R.drawable.buddies),
        Message("3", "Charlie", "Have you started that new hobby you mentioned?", R.drawable.buddies),
        Message("3", "Charlie", "Let’s team up for the next event!", R.drawable.buddies),
        Message("3", "Charlie", "Can you recommend some good movies?", R.drawable.buddies),
        Message("3", "Charlie", "How are you feeling today?", R.drawable.buddies),
        Message("3", "Charlie", "Thanks for your advice the other day.", R.drawable.buddies),
        Message("3", "Charlie", "Let’s organize a group outing soon!", R.drawable.buddies),
        Message("3", "Charlie", "What’s something exciting that happened to you recently?", R.drawable.buddies),
        Message("3", "Charlie", "Catch you later, Charlie!", R.drawable.buddies),

        // Messages for Angel
        Message("4", "Angel", "Hi Angel, how’s everything?", R.drawable.person1),
        Message("4", "Angel", "Did you see the news about our favorite team?", R.drawable.person1),
        Message("4", "Angel", "Can we schedule a call soon?", R.drawable.person1),
        Message("4", "Angel", "How’s your new project coming along?", R.drawable.person1),
        Message("4", "Angel", "What’s been inspiring you lately?", R.drawable.person1),
        Message("4", "Angel", "Thanks for always being so supportive.", R.drawable.person1),
        Message("4", "Angel", "Let’s catch up over coffee soon!", R.drawable.person1),
        Message("4", "Angel", "What’s your plan for the weekend?", R.drawable.person1),
        Message("4", "Angel", "I appreciate all the positivity you bring.", R.drawable.person1),
        Message("4", "Angel", "Take care, Angel!", R.drawable.person1),

        // Messages for Demon
        Message("5", "Demon", "Hey Demon, how’s life?", R.drawable.person2),
        Message("5", "Demon", "What’s your next big adventure?", R.drawable.person2),
        Message("5", "Demon", "I’ve been thinking about the fun times we’ve had.", R.drawable.person2),
        Message("5", "Demon", "Do you have any recommendations for a good book?", R.drawable.person2),
        Message("5", "Demon", "How’s work treating you these days?", R.drawable.person2),
        Message("5", "Demon", "Let me know if you’re free to hang out.", R.drawable.person2),
        Message("5", "Demon", "I value your insights, Demon.", R.drawable.person2),
        Message("5", "Demon", "Thanks for being such a great friend.", R.drawable.person2),
        Message("5", "Demon", "What’s something fun you’ve done recently?", R.drawable.person2),
        Message("5", "Demon", "Talk soon, take care!", R.drawable.person2),

        // Messages for Devil
        Message("6", "Devil", "Hello Devil, what’s up?", R.drawable.person3),
        Message("6", "Devil", "How are you finding time to relax lately?", R.drawable.person3),
        Message("6", "Devil", "I’d love to hear about your recent projects.", R.drawable.person3),
        Message("6", "Devil", "Let me know if you want to join us for dinner!", R.drawable.person3),
        Message("6", "Devil", "What’s your current favorite TV show?", R.drawable.person3),
        Message("6", "Devil", "Thanks for your help last week, it meant a lot.", R.drawable.person3),
        Message("6", "Devil", "Can you suggest a good travel destination?", R.drawable.person3),
        Message("6", "Devil", "What’s your most exciting memory from this year?", R.drawable.person3),
        Message("6", "Devil", "Looking forward to catching up again soon.", R.drawable.person3),
        Message("6", "Devil", "Take care, Devil. Talk soon!", R.drawable.person3)
    )
}


