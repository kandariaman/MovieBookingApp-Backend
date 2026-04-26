package com.example.demo.model;

public class ReservationRequest {
     private Long userId;
        private Long showId;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }

        public Long getShowId() { return showId; }
        public void setShowId(Long showId) { this.showId = showId; }
    }
