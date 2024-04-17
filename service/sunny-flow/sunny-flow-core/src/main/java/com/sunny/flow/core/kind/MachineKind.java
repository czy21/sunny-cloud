package com.sunny.flow.core.kind;

import lombok.Data;

public class MachineKind {


    public enum State {
        S1,
        S2,
        S3
    }

    public enum Event{
        E1,
        E2,
        E3
    }
}
