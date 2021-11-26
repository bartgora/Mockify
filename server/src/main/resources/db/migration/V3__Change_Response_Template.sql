ALTER TABLE hook
ADD response_template TEXT;

ALTER TABLE hook
DROP
CONSTRAINT fk_hook_on_responsetemplate;

ALTER TABLE hook
DROP
COLUMN response_template_id;