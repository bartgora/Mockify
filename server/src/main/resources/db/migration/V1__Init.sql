CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE event
(
    id          BIGINT NOT NULL,
    request_id  BIGINT,
    response_id BIGINT,
    CONSTRAINT pk_event PRIMARY KEY (id)
);

CREATE TABLE hook
(
    id                   BIGINT NOT NULL,
    name                 VARCHAR(255),
    response_template_id BIGINT,
    CONSTRAINT pk_hook PRIMARY KEY (id)
);

CREATE TABLE hook_events
(
    hook_id   BIGINT NOT NULL,
    events_id BIGINT NOT NULL
);

CREATE TABLE request
(
    id      BIGINT NOT NULL,
    method  VARCHAR(255),
    body    VARCHAR(255),
    headers VARCHAR(255),
    CONSTRAINT pk_request PRIMARY KEY (id)
);

CREATE TABLE response
(
    id   BIGINT NOT NULL,
    body VARCHAR(255),
    CONSTRAINT pk_response PRIMARY KEY (id)
);

ALTER TABLE hook_events
    ADD CONSTRAINT uc_hook_events_events UNIQUE (events_id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_REQUEST FOREIGN KEY (request_id) REFERENCES request (id);

ALTER TABLE event
    ADD CONSTRAINT FK_EVENT_ON_RESPONSE FOREIGN KEY (response_id) REFERENCES response (id);

ALTER TABLE hook
    ADD CONSTRAINT FK_HOOK_ON_RESPONSETEMPLATE FOREIGN KEY (response_template_id) REFERENCES response (id);

ALTER TABLE hook_events
    ADD CONSTRAINT fk_hooeve_on_event FOREIGN KEY (events_id) REFERENCES event (id);

ALTER TABLE hook_events
    ADD CONSTRAINT fk_hooeve_on_hook FOREIGN KEY (hook_id) REFERENCES hook (id);