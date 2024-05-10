import dk.sdu.mmmi.cbse.bulletsystem.BulletProcess;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.common.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with BulletPlugin;
    provides BulletSPI with BulletProcess;
    provides IEntityProcessingService with BulletProcess;
}