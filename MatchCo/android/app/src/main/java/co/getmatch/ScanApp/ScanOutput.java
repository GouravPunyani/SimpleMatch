package co.getmatch.ScanApp;

/*
 Copyright (C) 2013-2017 MatchCo Inc
 All rights reserved.
 This document is proprietary information of MatchCo Inc.
 Do not distribute outside of the company without written authorization.
 Portions of this document may be protected by patent.
 Portions of this document may be covered by patent pending.
 */

/**
 * Created by young.harvill on 2/27/2017.
 */

public class ScanOutput {
    PsSample whitePoint;
    PsSample underWrist;
    PsSample leftCheek;
    PsSample rightCheek;
    PsSample rightJawline;
    PsSample leftJawline;
    CviColor faceDisplayColor;
    char platform[];
    char appVersion[];
    char osVersion[];
}
