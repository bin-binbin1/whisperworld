package com.example.whisperworld.service;

@Repository
public class test_sample_s_class implements test_sample_s{
    @Autowired
    private SqlSession sqlSession;

    @Override
    public User findById(Long id) {
        return sqlSession.selectOne("com.example.mapper.UserMapper.findById", id);
    }

    @Override
    public List<User> findAll() {
        return sqlSession.selectList("com.example.mapper.UserMapper.findAll");
    }

    @Override
    public void save(User user) {
        sqlSession.insert("com.example.mapper.UserMapper.save", user);
    }

    @Override
    public void update(User user) {
        sqlSession.update("com.example.mapper.UserMapper.update", user);
    }

    @Override
    public void delete(Long id) {
        sqlSession.delete("com.example.mapper.UserMapper.delete", id);
    }
}
