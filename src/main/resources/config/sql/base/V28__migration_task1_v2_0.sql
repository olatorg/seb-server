-- ----------------------------------------------------------------
-- Add connection_id to lms_setup table SEBSERV-417
-- ----------------------------------------------------------------

ALTER TABLE `lms_setup`
ADD COLUMN IF NOT EXISTS `connection_id` VARCHAR(255) NULL;

-- ----------------------------------------------------------------
-- Add clipboard policy to GUI SEBSERV-534
-- ----------------------------------------------------------------
UPDATE orientation SET y_position=21 WHERE config_attribute_id=1578 AND template_id=0;
INSERT IGNORE INTO orientation (config_attribute_id, template_id, view_id, group_id, x_position, y_position, width, height, title) VALUES
    (1201, 0, 9, 'clipboardPolicy', 7, 18, 5, 2, 'NONE');