DO $$
DECLARE
v_user_id UUID;
  v_teacher_id UUID;
BEGIN
SELECT id INTO v_user_id FROM users WHERE email = 'teacher@hei.test';

IF v_user_id IS NULL THEN
    RAISE EXCEPTION 'Aucun user avec email teacher@hei.test — vérifie que V43_2 a bien tourné avant celle-ci';
END IF;

SELECT id INTO v_teacher_id FROM teachers WHERE user_id = v_user_id;
IF v_teacher_id IS NULL THEN
    v_teacher_id := gen_random_uuid();
INSERT INTO teachers (id, user_id) VALUES (v_teacher_id, v_user_id);
END IF;

INSERT INTO course_assignment (id, course_id, teacher_id, group_id) VALUES
                                                                        (gen_random_uuid(), '17c29a83-98c0-794d-043f-5b2240081b24', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '17c29a83-98c0-794d-043f-5b2240081b24', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '17c29a83-98c0-794d-043f-5b2240081b24', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), 'e22e08da-484a-d516-d9e7-520016eb42e5', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), 'e22e08da-484a-d516-d9e7-520016eb42e5', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), 'e22e08da-484a-d516-d9e7-520016eb42e5', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '4f7f4b49-8645-d03e-1597-33469495048f', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '4f7f4b49-8645-d03e-1597-33469495048f', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '4f7f4b49-8645-d03e-1597-33469495048f', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), 'd097427f-156a-961d-3e0c-576ae26996dd', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), 'd097427f-156a-961d-3e0c-576ae26996dd', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), 'd097427f-156a-961d-3e0c-576ae26996dd', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '291c6e82-2c6a-ef1f-ee8f-14f6a10ca4cc', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '291c6e82-2c6a-ef1f-ee8f-14f6a10ca4cc', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '291c6e82-2c6a-ef1f-ee8f-14f6a10ca4cc', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '63750107-1b4d-57dd-5555-cf3047d0293b', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '63750107-1b4d-57dd-5555-cf3047d0293b', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '63750107-1b4d-57dd-5555-cf3047d0293b', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), 'ca40c85d-0254-d402-25d8-22a21309503d', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), 'ca40c85d-0254-d402-25d8-22a21309503d', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), 'ca40c85d-0254-d402-25d8-22a21309503d', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '3a17e5e8-1912-d255-834b-473e230d84fe', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '3a17e5e8-1912-d255-834b-473e230d84fe', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '3a17e5e8-1912-d255-834b-473e230d84fe', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '46e42a92-ca94-a76b-2cd8-9e0142bb6043', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '46e42a92-ca94-a76b-2cd8-9e0142bb6043', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '46e42a92-ca94-a76b-2cd8-9e0142bb6043', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '2c1f937d-06be-14a8-b1bd-e4271dde535b', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '2c1f937d-06be-14a8-b1bd-e4271dde535b', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '2c1f937d-06be-14a8-b1bd-e4271dde535b', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), 'f705f658-1170-6769-9726-1b2d9cd5fe27', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), 'f705f658-1170-6769-9726-1b2d9cd5fe27', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), 'f705f658-1170-6769-9726-1b2d9cd5fe27', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '7b280efd-e43e-4721-7e5a-61c208c04c24', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '7b280efd-e43e-4721-7e5a-61c208c04c24', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '7b280efd-e43e-4721-7e5a-61c208c04c24', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '0a6c0364-8435-ff39-fef3-f29c2f875352', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '0a6c0364-8435-ff39-fef3-f29c2f875352', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '0a6c0364-8435-ff39-fef3-f29c2f875352', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '553769c7-2dda-1dd4-d5a5-a6789cbad098', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '553769c7-2dda-1dd4-d5a5-a6789cbad098', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '553769c7-2dda-1dd4-d5a5-a6789cbad098', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '19052e18-fe1a-fe12-bde8-6cb243e29e5b', v_teacher_id, '33333333-0000-0000-0000-000000000001'),
                                                                        (gen_random_uuid(), '19052e18-fe1a-fe12-bde8-6cb243e29e5b', v_teacher_id, '33333333-0000-0000-0000-000000000002'),
                                                                        (gen_random_uuid(), '19052e18-fe1a-fe12-bde8-6cb243e29e5b', v_teacher_id, '33333333-0000-0000-0000-000000000003'),
                                                                        (gen_random_uuid(), '9817aeed-947f-d46b-c56e-19d01e79c6eb', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), '9817aeed-947f-d46b-c56e-19d01e79c6eb', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), '9817aeed-947f-d46b-c56e-19d01e79c6eb', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), 'd589f9b7-b268-7549-96c8-b6643811960f', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), 'd589f9b7-b268-7549-96c8-b6643811960f', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), 'f465ee06-a782-9c97-dbcd-a3d6d837e8dd', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), 'f465ee06-a782-9c97-dbcd-a3d6d837e8dd', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), '370b5ec9-764b-3c69-f5a1-7e4a602ee598', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), '370b5ec9-764b-3c69-f5a1-7e4a602ee598', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), 'bac5136a-89f5-142b-c212-7bcfbce52e02', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), '92103cc0-cc78-bb0d-a7d6-7052c2ae0d8f', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), 'c8db2b3a-1bd1-3c90-b43a-5ab253af61f0', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), '14a9c42a-2cb5-a78d-2942-2cbc276f95c6', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), 'c658634a-673b-13ac-782a-cb71b3655ea7', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), 'c658634a-673b-13ac-782a-cb71b3655ea7', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), 'c658634a-673b-13ac-782a-cb71b3655ea7', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), '0fd8dd63-e7f2-c745-473a-db946afccc39', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), '0fd8dd63-e7f2-c745-473a-db946afccc39', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), '0fd8dd63-e7f2-c745-473a-db946afccc39', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), 'c9a2bf4e-a18e-5e1f-0fd3-c27cc4133679', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), 'c9a2bf4e-a18e-5e1f-0fd3-c27cc4133679', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), 'c9a2bf4e-a18e-5e1f-0fd3-c27cc4133679', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), '45e05e62-da39-9cbb-4160-a8b1a9f86b63', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), '45e05e62-da39-9cbb-4160-a8b1a9f86b63', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), '45e05e62-da39-9cbb-4160-a8b1a9f86b63', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), '090a8cab-cfd5-f8be-6bc8-ca749a7fffc6', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), '090a8cab-cfd5-f8be-6bc8-ca749a7fffc6', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), 'c21685d0-54df-183e-3dd3-aa81de26e82e', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), 'c21685d0-54df-183e-3dd3-aa81de26e82e', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), 'fa6d632b-c2ec-394b-9628-2b3d54bd880f', v_teacher_id, '33333333-0000-0000-0000-000000000004'),
                                                                        (gen_random_uuid(), 'fa6d632b-c2ec-394b-9628-2b3d54bd880f', v_teacher_id, '33333333-0000-0000-0000-000000000005'),
                                                                        (gen_random_uuid(), '3eaf43e8-1179-1d05-597b-44ec375275b8', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), 'f117d6af-93ff-e45b-004f-c7c1de5dd23c', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), '6e43b42a-6fbf-5f1b-b95b-8c6bd8561626', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), 'd9d66123-6512-1a69-3c3c-138e03d7d436', v_teacher_id, '33333333-0000-0000-0000-000000000006'),
                                                                        (gen_random_uuid(), 'a6f6e76e-2fb1-acde-f993-7c50cf82053d', v_teacher_id, '33333333-0000-0000-0000-000000000006');
END $$;