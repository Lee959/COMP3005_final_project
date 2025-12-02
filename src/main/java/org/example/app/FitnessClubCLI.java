package org.example.app;

import org.example.model.*;
import org.example.model.utils.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class FitnessClubCLI {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("   Health & Fitness Club Management System     ");

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    memberMenu();
                    break;
                case 2:
                    trainerMenu();
                    break;
                case 3:
                    adminMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nThank you for using Fitness Club Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        HibernateUtil.shutdown();
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Member Functions");
        System.out.println("2. Trainer Functions");
        System.out.println("3. Admin Functions");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
    }
    
    //Menu for Member function
    private static void memberMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("MEMBER MENU");
            System.out.println("=".repeat(50));
            System.out.println("1. Register New Member");
            System.out.println("2. Update Profile");
            System.out.println("3. Add Fitness Goal");
            System.out.println("4. Log Health Metrics");
            System.out.println("5. View Dashboard");
            System.out.println("6. Book Personal Training Session");
            System.out.println("7. Generate a Member (For Testing)"); // Testing
            System.out.println("0. Back to Main Menu");
            System.out.println("=".repeat(50));

            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerMember();
                    break;
                case 2:
                    updateMemberProfile();
                    break;
                case 3:
                    addFitnessGoal();
                    break;
                case 4:
                    logHealthMetrics();
                    break;
                case 5:
                    viewMemberDashboard();
                    break;
                case 6:
                    bookTrainingSession();
                    break;
                case 7:
                    generateMember(); //For Testing
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
    
    //Menu for Trainer functions
    private static void trainerMenu() {
        while (true) {
            System.out.println("\n--- Trainer Menu ---");
            System.out.println("1. Set Availability");
            System.out.println("2. View Schedule");
            System.out.println("3. Member Lookup");
            System.out.println("4. Generate a Trainer (Testing)");

            System.out.println("0. Back");

            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    setTrainerAvailability();
                    break;
                case 2:
                    viewTrainerSchedule();
                    break;
                case 3:
                    lookupMember();
                    break;
                case 4:
                    generateTrainer();
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    //Menu for Admin functions
    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Equipment Maintenance");
            System.out.println("2. Class Management");
            System.out.println("3. Generate Admin Staff (testing)");
            System.out.println("4. Generate Room (testing)");
            System.out.println("0. Back");

            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1:
                    manageEquipmentMaintenance();
                    break;
                case 2:
                    manageClasses();
                    break;
                case 3:
                    generateAdminStaff();
                case 4:
                    generateRoom();
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    // ******************* FOLLOWING ARE MEMBER FUNCTIONS IN MEMBER MENU ********************
    // OPTION 1. Registers member basic information
    private static void registerMember() {
        System.out.println("\nREGISTER NEW MEMBER");
        System.out.println("-".repeat(50));

        String email = getStringInput("Email: ");
        String password = getStringInput("Password: ");
        String firstName = getStringInput("First Name: ");
        String lastName = getStringInput("Last Name: ");
        String gender = getStringInput("Gender: ");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Check if email already exists
            Query<Long> query = session.createQuery("SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();

            if (count > 0) {
                System.out.println("Email already exists!");
                return;
            }

            Member member = new Member(email, password, firstName, lastName, gender);
            session.persist(member);
            transaction.commit();

            System.out.println("Member registered successfully! \n" + member);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    // OPTION 2. Updates member profile
    private static void updateMemberProfile() {
        System.out.println("\nUPDATE MEMBER PROFILE");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Member member = session.find(Member.class, memberId); // find the Member
            if (member == null) {
                System.out.println("Member not found!");
                return;
            }

            System.out.println("Current Profile: " + member.getFirst_name() + " " + member.getLast_name());
            System.out.println("Leave blank to keep current value.");

            String firstname = getStringInput("New First Name (current: " + member.getFirst_name() + "): ");
            if (!firstname.isEmpty()) member.setFirst_name(firstname);

            String lastname = getStringInput("New Last Name (current: " + member.getLast_name() + "): ");
            if (!lastname.isEmpty()) member.setLast_name(lastname);

            String password = getStringInput("New Password (current: " + member.getPassword() + "): ");
            if (!password.isEmpty()) member.setLast_name(password);


            session.merge(member);
            transaction.commit(); //save change

            System.out.println("Profile updated successfully!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    // OPTION 3. Creates fitness goals for member
    private static void addFitnessGoal() {
        System.out.println("\nADD FITNESS GOAL");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");
        String goalType = getStringInput("Goal Type (e.g., Weight Loss, Muscle Gain): ");
        BigDecimal targetValue = getBigDecimalInput("Target Value (e.g., 75.5 kg): ");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Member member = session.find(Member.class, memberId);
            if (member == null) {
                System.out.println("Member not found!");
                return;
            }


            FitnessGoal goal_generateDate = new FitnessGoal(
                    member,
                    goalType,
                    targetValue
            );

            session.persist(goal_generateDate);
            transaction.commit();

            System.out.println("Fitness goal added successfully!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    // OPTION 4. Record Health Metrics
    private static void logHealthMetrics() {
        System.out.println("\nLOG HEALTH METRICS");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");
        BigDecimal weight = getBigDecimalInput("Weight (kg): ");
        Integer heartRate = getIntInput("Heart Rate (bpm): ");
        BigDecimal bodyFat = getBigDecimalInput("Body Fat Percentage: ");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Member member = session.find(Member.class, memberId);
            if (member == null) {
                System.out.println("Member not found!");
                return;
            }

            HealthMetric metric = new HealthMetric(member);
            metric.setWeight(weight);
            metric.setHeartRate(heartRate);
            metric.setBodyFatPercentage(bodyFat);

            session.persist(metric);
            transaction.commit();

            System.out.println("Health metrics logged successfully!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    // OPTION 5. Show member dashboard with all information
    private static void viewMemberDashboard() {
        System.out.println("\n MEMBER DASHBOARD");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Member member = session.find(Member.class, memberId);
            if (member == null) {
                System.out.println("Member not found!");
                return;
            }

            // Display all member information
            System.out.println("\n" + "=".repeat(50));
            System.out.println("MEMBER PROFILE");
            System.out.println("=".repeat(50));
            System.out.println("ID: " + member.getMember_id());
            System.out.println("Name: " + member.getFirst_name() + " " + member.getLast_name());
            System.out.println("Email: " + member.getEmail());
            System.out.println("Gender: " + (member.getGender() != null ? member.getGender() : "N/A"));
            System.out.println("Registration Date: " + member.getRegisteration_date().toLocalDate());

            // Latest health metrics
            System.out.println("\n" + "=".repeat(50));
            System.out.println(" HEALTH METRICS");
            System.out.println("=".repeat(50));

            Query<HealthMetric> metricQuery = session.createQuery(
                    "FROM HealthMetric WHERE member.member_id = :memberId ORDER BY recordedDate DESC",
                    HealthMetric.class
            );
            metricQuery.setParameter("memberId", memberId);
            metricQuery.setMaxResults(1);
            List<HealthMetric> metrics = metricQuery.getResultList();

            if (!metrics.isEmpty()) {
                HealthMetric latest = metrics.get(0);
                System.out.println("Last Recorded: " + latest.getRecordedDate().toLocalDate());
                System.out.println("Weight: " + (latest.getWeight() != null ? latest.getWeight() + " kg" : "N/A"));
                System.out.println("Heart Rate: " + (latest.getHeartRate() != null ? latest.getHeartRate() + " bpm" : "N/A"));
                System.out.println("Body Fat: " + (latest.getBodyFatPercentage() != null ? latest.getBodyFatPercentage() + "%" : "N/A"));
            } else {
                System.out.println("No health metrics recorded yet.");
            }

            // Active goals
            System.out.println("\n" + "=".repeat(50));
            System.out.println("FITNESS GOALS");
            System.out.println("=".repeat(50));

            Query<FitnessGoal> goalQuery = session.createQuery(
                    "FROM FitnessGoal WHERE member.member_id = :memberId ORDER BY createdDate DESC",
                    FitnessGoal.class
            );
            goalQuery.setParameter("memberId", memberId);
            List<FitnessGoal> goals = goalQuery.getResultList();

            if (!goals.isEmpty()) {
                for (FitnessGoal goal : goals) {
                    System.out.println("• " + goal.getGoal_type() +
                            " - Target: " + goal.getTargetValue() +
                            " by " + goal.getTargetDate());
                }
            } else {
                System.out.println("No fitness goals set yet.");
            }

            // Upcoming sessions
            System.out.println("\n" + "=".repeat(50));
            System.out.println("UPCOMING TRAINING SESSIONS");
            System.out.println("=".repeat(50));

            Query<PersonalTrainingSession> sessionQuery = session.createQuery(
                    "FROM PersonalTrainingSession WHERE member.member_id = :memberId " +
                            "AND sessionDate >= CURRENT_DATE ORDER BY sessionDate, startTime",
                    PersonalTrainingSession.class
            );
            sessionQuery.setParameter("memberId", memberId);
            List<PersonalTrainingSession> sessions = sessionQuery.getResultList();

            if (!sessions.isEmpty()) {
                for (PersonalTrainingSession trainingSession : sessions) {
                    System.out.println("• " + trainingSession.getSessionDate() +
                            " at " + trainingSession.getStartTime()
                            + " - Trainer: " + trainingSession.getTrainer().getFirstName() + " " +
                            trainingSession.getTrainer().getLastName()
                          + " (Room: " + trainingSession.getRoom().getRoomName() + ")"
                    );
                }
            } else {
                System.out.println("No upcoming training sessions scheduled.");
            }

            System.out.println("=".repeat(50));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // OPTION 6. Book Training Session
    private static void bookTrainingSession() {
        System.out.println("\nBOOK PERSONAL TRAINING SESSION");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");
        Long trainerId = getLongInput("Enter Trainer ID: ");
        Long roomId = getLongInput("Enter Room ID: ");

        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();

            Member member = session.find(Member.class, memberId);
            Trainer trainer = session.find(Trainer.class, trainerId);
            Room room = session.find(Room.class, roomId);

            if (member == null || trainer == null || room == null) {
                System.out.println("Invalid Member, Trainer, or Room ID!");
                if (transaction != null) {
                    transaction.rollback();
                }
                return;
            }

            PersonalTrainingSession pts = new PersonalTrainingSession();
            pts.setMember(member);
            pts.setTrainer(trainer);
            pts.setRoom(room);
            // Set any other required fields (e.g., session date/time if needed)

            session.persist(pts);
            transaction.commit();

            System.out.println("Training session booked successfully! Session ID: " + pts.getSessionId());
            System.out.println("Success!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace(); // Helpful for debugging
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    //********************* FOLLOWING  ARE TRAINER FUNCTIONS IN TRAINER MENU ***********************
    // OPTION 1. Look up trainer member
    private static void lookupMember() {
        System.out.println("\nMEMBER LOOKUP (Trainer)");
        System.out.println("-".repeat(50));

        String search = getStringInput("Enter part of member's first or last name: ").toLowerCase();
        if (search.isEmpty()) {
            System.out.println("Search text cannot be empty.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // 1) Find members whose first_name or last_name matches (case-insensitive)
            Query<Member> memberQuery = session.createQuery(
                    "FROM Member m " +
                            "WHERE lower(m.first_name) LIKE :q " +
                            "   OR lower(m.last_name)  LIKE :q",
                    Member.class
            );
            memberQuery.setParameter("q", "%" + search + "%");

            List<Member> members = memberQuery.getResultList();

            if (members.isEmpty()) {
                System.out.println("No members found with that name.");
                return;
            }

            System.out.println("\nFound " + members.size() + " member(s):\n");

            for (Member member : members) {
                System.out.println("--------------------------------------------------");
                System.out.println("Member ID: " + member.getMember_id());
                System.out.println("Name     : " + member.getFirst_name() + " " + member.getLast_name());
                System.out.println("Email    : " + member.getEmail());

                // 2) Latest FitnessGoal for this member (current goal)
                Query<FitnessGoal> goalQuery = session.createQuery(
                        "FROM FitnessGoal fg " +
                                "WHERE fg.member = :member " +
                                "ORDER BY fg.createdDate DESC",
                        FitnessGoal.class
                );
                goalQuery.setParameter("member", member);
                goalQuery.setMaxResults(1);
                List<FitnessGoal> goals = goalQuery.getResultList();

                if (!goals.isEmpty()) {
                    FitnessGoal goal = goals.get(0);
                    System.out.println("\nCurrent Goal:");
                    System.out.println("  Type   : " + goal.getGoal_type());
                    System.out.println("  Target : " + goal.getTargetValue());
                    System.out.println("  Target Date: " + goal.getTargetDate());
                } else {
                    System.out.println("\nCurrent Goal: none set.");
                }

                // 3) Latest HealthMetric (last metric)
                Query<HealthMetric> metricQuery = session.createQuery(
                        "FROM HealthMetric hm " +
                                "WHERE hm.member = :member " +
                                "ORDER BY hm.recordedDate DESC",
                        HealthMetric.class
                );
                metricQuery.setParameter("member", member);
                metricQuery.setMaxResults(1);
                List<HealthMetric> metrics = metricQuery.getResultList();

                if (!metrics.isEmpty()) {
                    HealthMetric latest = metrics.get(0);
                    System.out.println("\nLast Recorded Metric:");
                    System.out.println("  Date      : " + latest.getRecordedDate());
                    System.out.println("  Weight    : " +
                            (latest.getWeight() != null ? latest.getWeight() + " kg" : "N/A"));
                    System.out.println("  HeartRate : " +
                            (latest.getHeartRate() != null ? latest.getHeartRate() + " bpm" : "N/A"));
                    System.out.println("  Body Fat% : " +
                            (latest.getBodyFatPercentage() != null ? latest.getBodyFatPercentage() + "%" : "N/A"));
                } else {
                    System.out.println("\nLast Recorded Metric: none recorded.");
                }
            }

            System.out.println("--------------------------------------------------");
        }
        catch (Exception e) {
            System.out.println("Error during member lookup: " + e.getMessage());
        }
    }

    // OPTION 2. Ssee Trainer's schedule
    private static void viewTrainerSchedule() {
        System.out.println("\nTRAINER SCHEDULE VIEW");
        System.out.println("-".repeat(50));

        long trainerId = getLongInput("Enter Trainer ID: ");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Trainer trainer = session.find(Trainer.class, trainerId);
            if (trainer == null) {
                System.out.println("No trainer found with ID " + trainerId);
                return;
            }

            System.out.println("\nSchedule for Trainer: " + trainerId);
            System.out.println("--------------------------------------------------");

            // 1) Personal Training Sessions
            Query<PersonalTrainingSession> ptsQuery = session.createQuery(
                    "FROM PersonalTrainingSession pts " +
                            "WHERE pts.trainer = :trainer " +
                            "ORDER BY pts.sessionDate, pts.startTime",
                    PersonalTrainingSession.class
            );
            ptsQuery.setParameter("trainer", trainer);
            List<PersonalTrainingSession> ptsList = ptsQuery.getResultList();

            if (ptsList.isEmpty()) {
                System.out.println("No personal training sessions assigned.");
            } else {
                System.out.println("Personal Training Sessions:");
                for (PersonalTrainingSession pts : ptsList) {
                    System.out.println("  " + pts);
                }
            }

            System.out.println("--------------------------------------------------");

            // 2) Group Classes
            Query<GroupClass> classQuery = session.createQuery(
                    "FROM GroupClass gc " +
                            "WHERE gc.trainer = :trainer " +
                            "ORDER BY gc.classDate, gc.startTime",
                    GroupClass.class
            );
            classQuery.setParameter("trainer", trainer);
            List<GroupClass> classList = classQuery.getResultList();

            if (classList.isEmpty()) {
                System.out.println("No group classes assigned.");
            } else {
                System.out.println("Group Classes:");
                for (GroupClass gc : classList) {
                    System.out.println("  " + gc);
                }
            }

            System.out.println("--------------------------------------------------");

        }
        catch (Exception e) {
            System.out.println("Error while viewing trainer schedule: " + e.getMessage());
        }
    }

    // OPTION 3. Set Availability for Trainer
    private static void setTrainerAvailability() {
        System.out.println("\nSET TRAINER AVAILABILITY");
        System.out.println("-".repeat(50));

        long trainerId = getLongInput("Enter Trainer ID: ");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Trainer trainer = session.find(Trainer.class, trainerId);
            if (trainer == null) {
                System.out.println("No trainer found with ID " + trainerId);
                return;
            }

            String dateStr = getStringInput("Enter availability date (YYYY-MM-DD): ");
            String startStr = getStringInput("Enter start time (HH:MM, 24h): ");
            String endStr   = getStringInput("Enter end time   (HH:MM, 24h): ");

            LocalDate date;
            LocalTime startTime;
            LocalTime endTime;

            try {
                date = LocalDate.parse(dateStr);
                startTime = LocalTime.parse(startStr);
                endTime = LocalTime.parse(endStr);
            } catch (Exception e) {
                System.out.println("Invalid date or time format. Please use YYYY-MM-DD and HH:MM.");
                return;
            }

            if (!endTime.isAfter(startTime)) {
                System.out.println("End time must be AFTER start time.");
                return;
            }

            // Check for overlap with existing availability
            Query<Long> overlapQuery = session.createQuery(
                    "SELECT COUNT(ta) " +
                            "FROM TrainerAvailability ta " +
                            "WHERE ta.trainer = :trainer " +
                            "  AND ta.date = :date " +
                            "  AND ta.startTime < :newEnd " +
                            "  AND ta.endTime   > :newStart",
                    Long.class
            );
            overlapQuery.setParameter("trainer", trainer);
            overlapQuery.setParameter("date", date);
            overlapQuery.setParameter("newEnd", endTime);
            overlapQuery.setParameter("newStart", startTime);

            Long overlaps = overlapQuery.uniqueResult();
            if (overlaps != null && overlaps > 0) {
                System.out.println("Cannot add availability: time window overlaps with an existing one.");
                return;
            }

            // No overlap → save new availability
            Transaction tx = session.beginTransaction();
            try {
                TrainerAvailability availability =
                        new TrainerAvailability(trainer, date, startTime, endTime);
                session.persist(availability);
                tx.commit();
                System.out.println("Availability added successfully.");
            }
            catch (Exception e) {
                tx.rollback();
                System.out.println("Error saving availability: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error setting trainer availability: " + e.getMessage());
        }
    }


    //********************* FOLLOWING ADMIN FUNCTIONS IN ADMIN MENU ******************

    // OPTION 1. Manage and update classes
    private static void manageClasses() {
        while (true) {
            System.out.println("\n--- CLASS MANAGEMENT (Admin) ---");
            System.out.println("1. Create New Class");
            System.out.println("2. List All Classes");
            System.out.println("3. Update Existing Class");
            System.out.println("0. Back");

            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> createGroupClass();
                case 2 -> listGroupClasses();
                case 3 -> updateGroupClass();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // **** Helper methods for Manage classes ****
    // Create a Group Class
    private static void createGroupClass() {
        System.out.println("\nCREATE NEW GROUP CLASS");
        System.out.println("-".repeat(50));

        String name = getStringInput("Class name: ");
        String description = getStringInput("Description (optional): ");

        long trainerId = getLongInput("Trainer ID: ");
        long roomId    = getLongInput("Room ID: ");

        String dateStr  = getStringInput("Class date (YYYY-MM-DD): ");
        String startStr = getStringInput("Start time (HH:MM, 24h): ");
        String endStr   = getStringInput("End time (HH:MM, 24h): ");

        int capacity    = getIntInput("Capacity: ");

        LocalDate date;
        LocalTime startTime;
        LocalTime endTime;

        try {
            date = LocalDate.parse(dateStr);
            startTime = LocalTime.parse(startStr);
            endTime = LocalTime.parse(endStr);
        } catch (Exception e) {
            System.out.println("Invalid date/time format.");
            return;
        }

        if (!endTime.isAfter(startTime)) {
            System.out.println("End time must be after start time.");
            return;
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Trainer trainer = session.find(Trainer.class, trainerId);
            Room room       = session.find(Room.class, roomId);

            if (trainer == null) {
                System.out.println("Trainer not found.");
                return;
            }
            if (room == null) {
                System.out.println("Room not found.");
                return;
            }

            Transaction tx = session.beginTransaction();
            try {
                GroupClass groupClass = new GroupClass(
                        name,
                        description,
                        trainer,
                        room,
                        date,
                        startTime,
                        endTime,
                        capacity
                );
                session.persist(groupClass);
                tx.commit();
                System.out.println("Group class created with ID: " + groupClass.getClassId());
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error creating class: " + e.getMessage());
            }
        }
    }

    // Lists all group class
    private static void listGroupClasses() {
        System.out.println("\nLIST OF GROUP CLASSES");
        System.out.println("-".repeat(50));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<GroupClass> query = session.createQuery(
                    "FROM GroupClass gc ORDER BY gc.classDate, gc.startTime",
                    GroupClass.class
            );
            List<GroupClass> classes = query.getResultList();

            if (classes.isEmpty()) {
                System.out.println("No classes found.");
                return;
            }

            for (GroupClass gc : classes) {
                System.out.println(gc);   // relies on nice toString()
            }
        }
    }

    // Update the existing classes
    private static void updateGroupClass() {
        System.out.println("\nUPDATE GROUP CLASS");
        System.out.println("-".repeat(50));

        long classId = getLongInput("Enter class ID to update: ");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            GroupClass gc = session.find(GroupClass.class, classId);
            if (gc == null) {
                System.out.println("Class not found.");
                return;
            }

            System.out.println("Current class info:");
            System.out.println(gc);

            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Trainer");
            System.out.println("2. Room");
            System.out.println("3. Date/Time");
            System.out.println("4. Capacity");
            int choice = getIntInput("Choice: ");

            Transaction tx = session.beginTransaction();
            try {
                switch (choice) {
                    case 1 -> {
                        long newTrainerId = getLongInput("New trainer ID: ");
                        Trainer newTrainer = session.find(Trainer.class, newTrainerId);
                        if (newTrainer == null) {
                            System.out.println("Trainer not found.");
                            tx.rollback();
                            return;
                        }
                        gc.setTrainer(newTrainer);
                    }
                    case 2 -> {
                        long newRoomId = getLongInput("New room ID: ");
                        Room newRoom = session.find(Room.class, newRoomId);
                        if (newRoom == null) {
                            System.out.println("Room not found.");
                            tx.rollback();
                            return;
                        }
                        gc.setRoom(newRoom);
                    }
                    case 3 -> {
                        String dateStr  = getStringInput("New date (YYYY-MM-DD): ");
                        String startStr = getStringInput("New start time (HH:MM): ");
                        String endStr   = getStringInput("New end time (HH:MM): ");
                        try {
                            LocalDate newDate = LocalDate.parse(dateStr);
                            LocalTime newStart = LocalTime.parse(startStr);
                            LocalTime newEnd   = LocalTime.parse(endStr);
                            if (!newEnd.isAfter(newStart)) {
                                System.out.println("End time must be after start time.");
                                tx.rollback();
                                return;
                            }
                            gc.setClassDate(newDate);
                            gc.setStartTime(newStart);
                            gc.setEndTime(newEnd);
                        } catch (Exception e) {
                            System.out.println("Invalid date/time format.");
                            tx.rollback();
                            return;
                        }
                    }
                    case 4 -> {
                        int newCap = getIntInput("New capacity: ");
                        gc.setCapacity(newCap);
                    }
                    default -> {
                        System.out.println("Invalid choice.");
                        tx.rollback();
                        return;
                    }
                }
                session.merge(gc);
                tx.commit();
                System.out.println("Class updated successfully.");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error updating class: " + e.getMessage());
            }
        }
    }

    // OPTION 2. Manages equipments
    private static void manageEquipmentMaintenance() {
        while (true) {
            System.out.println("\n--- EQUIPMENT MAINTENANCE (Admin) ---");
            System.out.println("1. Add Equipment");
            System.out.println("2. Open Maintenance Ticket");
            System.out.println("3. Resolve Maintenance Ticket");
            System.out.println("4. List Open Tickets");
            System.out.println("0. Back");

            int choice = getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> addEquipment();
                case 2 -> openMaintenanceTicket();
                case 3 -> resolveMaintenanceTicket();
                case 4 -> listOpenTickets();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    //**** Helper methods for manage equipment ***
    private static void addEquipment() {
        System.out.println("\nADD EQUIPMENT");
        System.out.println("-".repeat(50));

        String name = getStringInput("Equipment name: ");
        long roomId = getLongInput("Room ID (0 if not assigned): ");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Room room = null;
            if (roomId != 0) {
                room = session.find(Room.class, roomId);
                if (room == null) {
                    System.out.println("Room not found; equipment will not be linked to a room.");
                }
            }

            Transaction tx = session.beginTransaction();
            try {
                Equipment equipment = new Equipment(name, room, "OK");
                session.persist(equipment);
                tx.commit();
                System.out.println("Equipment added with ID: " + equipment.getEquipmentId());
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error adding equipment: " + e.getMessage());
            }
        }
    }

    private static void openMaintenanceTicket() {
        System.out.println("\nOPEN MAINTENANCE TICKET");
        System.out.println("-".repeat(50));

        long equipmentId = getLongInput("Equipment ID: ");
        String desc = getStringInput("Issue description: ");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Equipment equipment = session.find(Equipment.class, equipmentId);
            if (equipment == null) {
                System.out.println("Equipment not found.");
                return;
            }

            Transaction tx = session.beginTransaction();
            try {
                MaintenanceTicket ticket = new MaintenanceTicket(equipment, desc);
                session.persist(ticket);
                // optional: also mark equipment as OutOfOrder
                equipment.setStatus("OUT_OF_ORDER");
                session.merge(equipment);

                tx.commit();
                System.out.println("Ticket opened with ID: " + ticket.getTicketId());
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error opening ticket: " + e.getMessage());
            }
        }
    }

    private static void resolveMaintenanceTicket() {
        System.out.println("\nRESOLVE MAINTENANCE TICKET");
        System.out.println("-".repeat(50));

        long ticketId = getLongInput("Ticket ID: ");

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            MaintenanceTicket ticket = session.find(MaintenanceTicket.class, ticketId);
            if (ticket == null) {
                System.out.println("Ticket not found.");
                return;
            }

            if ("RESOLVED".equalsIgnoreCase(ticket.getStatus())) {
                System.out.println("Ticket is already resolved.");
                return;
            }

            Transaction tx = session.beginTransaction();
            try {
                ticket.setStatus("RESOLVED");
                ticket.setResolvedAt(java.time.LocalDateTime.now());

                // Optional: mark equipment back to OK
                Equipment eq = ticket.getEquipment();
                if (eq != null) {
                    eq.setStatus("OK");
                    session.merge(eq);
                }

                session.merge(ticket);
                tx.commit();
                System.out.println("Ticket resolved.");
            } catch (Exception e) {
                tx.rollback();
                System.out.println("Error resolving ticket: " + e.getMessage());
            }
        }
    }

    private static void listOpenTickets() {
        System.out.println("\nOPEN MAINTENANCE TICKETS");
        System.out.println("-".repeat(50));

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MaintenanceTicket> query = session.createQuery(
                    "FROM MaintenanceTicket mt WHERE mt.status <> 'RESOLVED' ORDER BY mt.createdAt",
                    MaintenanceTicket.class
            );
            List<MaintenanceTicket> tickets = query.getResultList();

            if (tickets.isEmpty()) {
                System.out.println("No open tickets.");
                return;
            }

            for (MaintenanceTicket mt : tickets) {
                System.out.println(mt);
            }
        }
    }



    // Helper Function: Validate User's Input
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static Long getLongInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static BigDecimal getBigDecimalInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid decimal number.");
            }
        }
    }


    //************** THE FOLLOWING CODE ARE FOR TESTING AND DEMO PURPOSES ****************************
    // MEMBER MENU - OPTION 7
    // Generate random members into table to help testing
    public static void generateMember() {
        Transaction transaction = null;
        Member generated_member = new Member();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<Long> query = session.createQuery("SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class);
            query.setParameter("email", generated_member.getEmail());
            Long count = query.getSingleResult();
            if (count > 0) {
                System.out.println("Email already exists!");
                return;
            }
            session.persist(generated_member);
            transaction.commit();

            System.out.println("Member registered successfully! \n" + generated_member);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    // TRAINER MENU - OPTION 4
    // Generate random trainer to help testing
    public static void generateTrainer() {
        Transaction transaction = null;
        Trainer generated_trainer = new Trainer();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<Long> query = session.createQuery("SELECT COUNT(m) FROM Member m WHERE m.email = :email", Long.class);
            query.setParameter("email", generated_trainer.getEmail());
            Long count = query.getSingleResult();
            if (count > 0) {
                System.out.println("Email already exists!");
                return;
            }
            session.persist(generated_trainer);
            transaction.commit();

            System.out.println("Member registered successfully! \n" + generated_trainer);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ADMIN MENU - OPTION 3
    // Generate a admin staff to Test
    public static void generateAdminStaff() {
        Transaction transaction = null;
        AdminStaff generated_admin = new AdminStaff();   // uses your random constructor

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Check if email already exists
            Query<Long> query = session.createQuery(
                    "SELECT COUNT(a) FROM AdminStaff a WHERE a.email = :email",
                    Long.class
            );
            query.setParameter("email", generated_admin.getEmail());

            if (query.getSingleResult() > 0) {
                System.out.println("Admin email already exists!");
                return;
            }

            session.persist(generated_admin);
            transaction.commit();

            System.out.println("Admin staff registered successfully!\n" + generated_admin);

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ADMIN MENU - OPTION 4
    // Genearte random rooms so we can assign sessions
    private static void generateRoom() {
        Transaction transaction = null;
        Room room = new Room();   // uses the testing constructor (random name + capacity)

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(room);
            transaction.commit();

            System.out.println("\nRoom created successfully:");
            System.out.println("   ID:   " + room.getRoomId());
            System.out.println("   Name: " + room.getRoomName());
            System.out.println("   Cap:  " + room.getCapacity());

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error creating room: " + e.getMessage());
        }
    }
}
