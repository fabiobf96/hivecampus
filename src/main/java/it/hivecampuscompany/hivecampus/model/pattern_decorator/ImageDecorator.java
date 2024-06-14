package it.hivecampuscompany.hivecampus.model.pattern_decorator;

import it.hivecampuscompany.hivecampus.bean.HomeBean;
import it.hivecampuscompany.hivecampus.bean.RoomBean;

public class ImageDecorator<T> extends Decorator<T> {
    private final byte[] image;
    private String imageName;

    public ImageDecorator(Component<T> component, byte[] image) {
        super(component);
        this.image = image;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public T toBean() {
        T bean = super.toBean();
        if (bean instanceof RoomBean roomBean) {
            roomBean.setRoomImage(image);
            roomBean.setImageName(imageName);
        }
        if (bean instanceof HomeBean homeBean) {
            homeBean.setHomeImage(image);
            homeBean.setImageName(imageName);
        }
        return bean;
    }

    @Override
    public String toString() {
        return super.toString() + " ImageDecorator{" +
                "image=" + image.length +
                ", imageName='" + imageName + '\'' +
                '}';
    }
}
