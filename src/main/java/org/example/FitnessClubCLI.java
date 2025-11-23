package org.example;

import org.example.entity.*;
import org.example.utils.*;
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
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

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
//                case 2:
//                    trainerMenu();
//                    break;
//                case 3:
//                    adminMenu();
//                    break;
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
            System.out.println("7. Generate a Member (For Testing)");
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
                case 7: //For Testing
                    generateMember();
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    //1.
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

    //7.
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

    //2.
    private static void updateMemberProfile() {
        System.out.println("\nUPDATE MEMBER PROFILE");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Member member = session.find(Member.class, memberId);
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
            transaction.commit();

            System.out.println("✅ Profile updated successfully!");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
        }
    }

    //3.
    private static void addFitnessGoal() {
        System.out.println("\nADD FITNESS GOAL");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");
        String goalType = getStringInput("Goal Type (e.g., Weight Loss, Muscle Gain): ");
        BigDecimal targetValue = getBigDecimalInput("Target Value (e.g., 75.5 kg): ");
//        String targetDate = getStringInput("Target Date (yyyy-MM-dd): ");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Member member = session.find(Member.class, memberId);
            if (member == null) {
                System.out.println("Member not found!");
                return;
            }

//            FitnessGoal goal = new FitnessGoal(
//                    member,
//                    goalType,
//                    targetValue,
//                    LocalDate.parse(targetDate, dateFormatter)
//            );

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

    // 4. Log Health Metrics
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

    private static void viewMemberDashboard() {
        System.out.println("\n📈 MEMBER DASHBOARD");
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
//                            + " - Trainer: " + trainingSession.getTrainer().getFirstName() + " " +
//                            trainingSession.getTrainer().getLastName() +
//                            " (Room: " + trainingSession.getRoom().getRoomName() + ")"
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

    // 6. Book Training Session
    private static void bookTrainingSession() {
        System.out.println("\nBOOK PERSONAL TRAINING SESSION");
        System.out.println("-".repeat(50));

        Long memberId = getLongInput("Enter Member ID: ");
        Long trainerId = getLongInput("Enter Trainer ID: ");
        Long roomId = getLongInput("Enter Room ID: ");
//        String sessionDate = getStringInput("Session Date (yyyy-MM-dd): ");
//        String startTime = getStringInput("Start Time (HH:mm): ");
//        String endTime = getStringInput("End Time (HH:mm): ");

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Member member = session.find(Member.class, memberId);
            Trainer trainer = session.find(Trainer.class, trainerId);
            Room room = session.find(Room.class, roomId);

            if (member == null || trainer == null || room == null) {
                System.out.println("Invalid Member, Trainer, or Room ID!");
                return;
            }

            PersonalTrainingSession pts = new PersonalTrainingSession(member, trainer, room);
//            pts.setSessionDate(LocalDate.parse(sessionDate, dateFormatter));
//            pts.setStartTime(LocalTime.parse(startTime, timeFormatter));
//            pts.setEndTime(LocalTime.parse(endTime, timeFormatter));

            session.persist(pts);
            transaction.commit();

            System.out.println("Training session booked successfully! Session ID: " + pts.getSessionId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.out.println("Error: " + e.getMessage());
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
}
