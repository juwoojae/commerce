package commerce.hello.service.managerSevice;

import commerce.hello.SecurityException;
import commerce.hello.domain.member.Member;
import commerce.hello.domain.product.Category;
import commerce.hello.domain.product.Product;

import java.util.List;

public class ProxyManagerService implements ManagerService{
    private final String MANAGER_PASSWORD = "1q2w3e4r!@";
    private final ManagerService managerService;

    public ProxyManagerService(ManagerService managerService,String password) {
        if(MANAGER_PASSWORD.equals(password))
            this.managerService = managerService;
        else
            throw new SecurityException("암호 불일치"+ password);
    }


    @Override
    public Product register(Product product) {
        return managerService.register(product);
    }

    @Override
    public Product remove(String name) {
        return managerService.remove(name);
    }

    @Override
    public Product update(String name, Product product) {
        return managerService.update(name, product);
    }

    @Override
    public List<Product> ListProducts(Category category) {
        return managerService.ListProducts(category);
    }
}
