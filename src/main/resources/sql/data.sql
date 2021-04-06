INSERT INTO User ( name, lastname, username, password, email ) VALUES ( 'Daniel', 'Expósito', 'expox12', 'password123', 'expox12@gmail.com' );
INSERT INTO User ( name, lastname, username, password, email ) VALUES ( 'Carlos', 'Expósito', 'expox92', 'password321', 'expox92@gmail.com' );

INSERT INTO Role ( name ) VALUES ( 'ADMIN' );
INSERT INTO Role ( name ) VALUES ( 'USER' );

INSERT INTO Capability ( name ) VALUES ( 'CREATE_SOMETHING' );
INSERT INTO Capability ( name ) VALUES ( 'UPDATE_SOMETHING' );
INSERT INTO Capability ( name ) VALUES ( 'READ_SOMETHING' );

INSERT INTO users_roles ( user_id, role_id ) VALUES ( 1, 1 );
INSERT INTO users_roles ( user_id, role_id ) VALUES ( 2, 2 );

INSERT INTO roles_capabilities ( role_id, capability_id ) VALUES ( 1, 1 );
INSERT INTO roles_capabilities ( role_id, capability_id ) VALUES ( 1, 2 );
INSERT INTO roles_capabilities ( role_id, capability_id ) VALUES ( 2, 1 );

INSERT INTO users_capabilities ( user_id, capability_id ) VALUES ( 1, 3 );
-- INSERT INTO users_capabilities ( role_id, capability_id ) VALUES ( 2, 2 );

