
// Mobile Sidebar
const menuToggle = document.getElementById("menuToggle");
const sidebar = document.getElementById("sidebar");

menuToggle.addEventListener("click", function () {
    sidebar.classList.toggle("show");
});

// Buddy Search
const searchBuddyBtn = document.getElementById("searchBuddyBtn");
const searchMessage = document.getElementById("searchMessage");

searchBuddyBtn.addEventListener("click", function () {

    const course = document.getElementById("course").value;
    const branch = document.getElementById("branch").value;
    const interest = document.getElementById("interest").value;

    if (!course && !branch && !interest) {
        searchMessage.textContent =
            "Please select at least one search filter.";
        return;
    }

    searchMessage.textContent =
        "Search filters selected! Connect this to your Find Buddy feature.";
});

// Task Counter
const taskCheckboxes = document.querySelectorAll(".task-checkbox");
const taskCount = document.getElementById("taskCount");

function updateTaskCount() {

    let remainingTasks = 0;

    taskCheckboxes.forEach(function (checkbox) {
        if (!checkbox.checked) {
            remainingTasks++;
        }
    });

    taskCount.textContent = remainingTasks;
}

taskCheckboxes.forEach(function (checkbox) {
    checkbox.addEventListener("change", updateTaskCount);
});

// Initial Task Count
updateTaskCount();

// Notification Button
const notificationBtn = document.getElementById("notificationBtn");

notificationBtn.addEventListener("click", function () {
    alert("You have 2 new notifications!");
});
