# CinemaConnect – Smart Cinema Ticketing Mobile Application

CinemaConnect is an Android-based mobile application designed to modernize the cinema ticketing experience.  
The system enables users to discover cinemas, view movies, select seats, order snacks, make payments, and receive digital tickets, while providing administrators with real-time analytics and reporting tools.

The application addresses inefficiencies in traditional cinema booking systems such as long queues, manual ticket handling, lack of real-time reporting, and limited customer engagement.

---

##  What CinemaConnect Does

###  User Features
CinemaConnect allows users to:
- Browse nearby cinemas and available movies
- View movie details including trailers and descriptions
- Select cinema screens (Screen 1, 2, or 3)
- Choose seats using an interactive seating layout
- Order snacks before the movie
- Make payments using **MPESA or VISA (mock implementation)**
- Receive a **QR code ticket** for cinema entry
- View purchased tickets in their profile
  
<img width="866" height="1067" alt="Screenshot 2025-12-05 130217" src="https://github.com/user-attachments/assets/9a923b25-d155-416b-abc1-f901d2fc714b" />
<img width="834" height="1079" alt="Screenshot 2025-12-05 130328" src="https://github.com/user-attachments/assets/1f2ea001-8282-4fa8-9a94-e736cdb8443e" />
<img width="842" height="1079" alt="Screenshot 2025-12-05 130431" src="https://github.com/user-attachments/assets/5a012189-5ef1-492c-9d05-c7495cd40ddf" />
<img width="852" height="1079" alt="Screenshot 2025-12-05 130452" src="https://github.com/user-attachments/assets/2275eb84-9d91-473d-87fa-3481c1349b90" />
<img width="864" height="1079" alt="Screenshot 2025-12-05 130554" src="https://github.com/user-attachments/assets/f27b6cb3-c4ff-43df-98a9-93bceb0d3023" />
<img width="857" height="1078" alt="Screenshot 2025-12-05 130611" src="https://github.com/user-attachments/assets/b671f670-36f8-4d5f-bca7-1cb782aabc92" />
<img width="859" height="1079" alt="Screenshot 2025-12-05 130633" src="https://github.com/user-attachments/assets/00569535-9b24-4c72-90e8-b36949b72a72" />
<img width="856" height="1079" alt="Screenshot 2025-12-05 130647" src="https://github.com/user-attachments/assets/ac1bc23f-be59-4da3-a0d1-bbe79c41a85b" />
<img width="856" height="1079" alt="Screenshot 2025-12-05 130706" src="https://github.com/user-attachments/assets/76426d29-c4df-464c-9ab2-33ffdf94f072" />
<img width="853" height="1079" alt="Screenshot 2025-12-05 130721" src="https://github.com/user-attachments/assets/f2d72eb9-679c-49af-ac7d-2602e8f9ac42" />
<img width="858" height="1079" alt="Screenshot 2025-12-05 130734" src="https://github.com/user-attachments/assets/4b685b91-ddc9-47f1-ad34-273b0b11b735" />
<img width="867" height="1073" alt="Screenshot 2025-12-05 130800" src="https://github.com/user-attachments/assets/4bd0ce69-80ee-4a19-bb3c-f25806c57415" />
<img width="853" height="1079" alt="Screenshot 2025-12-05 130824" src="https://github.com/user-attachments/assets/556c37fc-3b1f-4c6d-81a3-5c77250327cb" />


### Admin Dashboard
CinemaConnect includes a secure admin dashboard that allows administrators to:
- View total registered users
- Monitor total tickets sold
- Track total and daily revenue
- Analyze ticket sales grouped by cinema and movie
- Visualize data using charts
- Export system reports as **PDF documents**
<img width="861" height="1079" alt="Screenshot 2025-12-05 130945" src="https://github.com/user-attachments/assets/d4efa9ae-2e45-4f90-b9a7-828ff9b8da5d" />
<img width="852" height="1079" alt="Screenshot 2025-12-05 131003" src="https://github.com/user-attachments/assets/b594c417-381b-4ce1-a9a1-9c379fff2b03" />
<img width="790" height="768" alt="Screenshot 2025-12-05 130930" src="https://github.com/user-attachments/assets/2d7c2446-a5da-40ba-8ff1-ba5113606b4b" />


##  Technologies Used

- **Android (Java)**
- **Firebase Authentication**
- **Cloud Firestore**
- **Google Maps API**
- **MPAndroidChart** (Data visualization)
- **ZXing** (QR Code generation)
- **YouTube Embed via WebView**
- **PDFDocument API** (Report generation)

---
##  Security Considerations

- Secure authentication via Firebase
- Role-based access control for admin features
- Firestore rules to restrict unauthorized access
- No real payment credentials stored (mock payments only)


